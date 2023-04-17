package bstrees.repos

import bstrees.BinarySearchTree
import bstrees.nodes.TreeNode
import bstrees.repos.strategies.SerializationStrategy
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction


private object NodesTable : IntIdTable("nodes") {
    val data = varchar("data", 255)
    val metadata = varchar("metadata", 255)
    val left = reference("left_id", NodesTable).nullable()
    val right = reference("right_id", NodesTable).nullable()
    val tree = reference("tree_id", TreesTable, onDelete = ReferenceOption.CASCADE)
}

internal class DBNode(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DBNode>(NodesTable)

    var data by NodesTable.data
    var metadata by NodesTable.metadata
    var left by DBNode optionalReferencedOn NodesTable.left
    var right by DBNode optionalReferencedOn NodesTable.right
    var tree by DBTree referencedOn NodesTable.tree
}

private object TreesTable : IntIdTable("trees") {
    val name = varchar("name", 255)
    val type = varchar("type", 80)
    val root = reference("root_node_id", NodesTable).nullable()

    init {
        uniqueIndex(name, type) // tree names must be unique for each tree type
    }
}

internal class DBTree(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DBTree>(TreesTable)

    var name by TreesTable.name
    var type by TreesTable.type
    var root by DBNode optionalReferencedOn TreesTable.root
}


/**
 * Saves binary search trees in SQL database [db].
 * Acts like associative array with trees' names as keys and trees as values.
 *
 * Must be provided with [strategy] so repository knows how to work with specific [TreeType].
 *
 * Different tree types can be saved in the same [db]
 * by creating several SqlRepositories with same [db].
 * Note that saving trees with same tree type but different data type [T]
 * in the same [db] is error-prone and is not advised.
 *
 * Usage of SQLite is not advised as 'ON DELETE CASCADE' doesn't seem to work properly there:
 * after deletion of tree its nodes remain in the table.
 */
class SqlRepository<T : Comparable<T>,
        NodeType : TreeNode<T, NodeType>,
        TreeType : BinarySearchTree<T, NodeType>>(
    private val strategy: SerializationStrategy<T, NodeType, TreeType>, private val db: Database
) : TreeRepository<TreeType> {
    private val bstType = strategy.bstType.toString()

    init {
        transaction(db) {
            SchemaUtils.create(TreesTable)
            SchemaUtils.create(NodesTable)
        }
    }

    override fun getNames(): List<String> = transaction(db) {
        DBTree.find(TreesTable.type eq bstType).map(DBTree::name)
    }

    override fun get(treeName: String): TreeType? = transaction(db) {
        DBTree.find(
            TreesTable.type eq bstType and (TreesTable.name eq treeName)
        ).firstOrNull()?.let {
            strategy.createTree().apply { root = it.root?.deserialize() }
        }
    }

    override fun set(treeName: String, tree: TreeType): Unit = transaction(db) {
        remove(treeName) // remove first if already exists

        val dbTree = DBTree.new {
            name = treeName
            type = bstType
        }
        dbTree.root = tree.root?.toDBNode(dbTree)
    }

    override fun remove(treeName: String): Boolean = transaction(db) {
        DBTree.find(
            TreesTable.type eq bstType and (TreesTable.name eq treeName)
        ).firstOrNull()?.let {
            it.delete()
            true
        } ?: false
    }

    private fun NodeType.toDBNode(dbTree: DBTree): DBNode =
        DBNode.new {
            data = strategy.collectData(this@toDBNode)
            metadata = strategy.collectMetadata(this@toDBNode)
            left = this@toDBNode.left?.toDBNode(dbTree)
            right = this@toDBNode.right?.toDBNode(dbTree)
            tree = dbTree
        }

    private fun DBNode.deserialize(parent: NodeType? = null): NodeType {
        val node = strategy.createNode(data)
        strategy.processMetadata(node, metadata)

        node.parent = parent
        node.left = left?.deserialize(node)
        node.right = right?.deserialize(node)

        return node
    }
}
