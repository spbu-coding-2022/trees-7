package bstrees.repos

import bstrees.BinarySearchTree
import bstrees.nodes.TreeNode
import bstrees.repos.strategies.SerializationStrategy
import org.neo4j.ogm.annotation.*
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.cypher.ComparisonOperator
import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.session.SessionFactory


@NodeEntity("Node")
private class GraphNode(
    @Property("data")
    var data: String,

    @Property("metadata")
    var metadata: String,

    @Relationship(type = "LEFT", direction = Relationship.Direction.OUTGOING)
    var left: GraphNode? = null,

    @Relationship(type = "RIGHT", direction = Relationship.Direction.OUTGOING)
    var right: GraphNode? = null
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}

@NodeEntity("Tree")
private class GraphTree(
    @Property("name")
    var name: String,

    @Property("type")
    var type: String,

    @Relationship(type = "ROOT", direction = Relationship.Direction.OUTGOING)
    var root: GraphNode?
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}


/**
 * Saves binary search trees in neo4j database.
 * Acts like associative array with trees' names as keys and trees as values.
 *
 * Must be provided with [strategy] so repository knows how to work with specific [TreeType].
 *
 * Different tree types can be saved in the same database
 * by creating several Neo4jRepositories with same neo4jConfig.
 * Note that saving trees with same tree type but different data type [T]
 * in the same database is error-prone and is not advised.
 */
class Neo4jRepository<T : Comparable<T>,
        NodeType : TreeNode<T, NodeType>,
        TreeType : BinarySearchTree<T, NodeType>>(
    private val strategy: SerializationStrategy<T, NodeType, TreeType>, neo4jConfig: Configuration
) : TreeRepository<TreeType> {
    private val session = SessionFactory(neo4jConfig, "bstrees.repos").openSession()
    private val bstType = strategy.bstType.toString()

    override fun getNames(): List<String> =
        session.loadAll(
            GraphTree::class.java,
            Filter("type", ComparisonOperator.EQUALS, bstType),
            0
        ).map(GraphTree::name)

    override fun get(treeName: String): TreeType? =
        session.loadAll(
            GraphTree::class.java,
            Filter("type", ComparisonOperator.EQUALS, bstType).and(
                Filter("name", ComparisonOperator.EQUALS, treeName)
            ),
            -1
        ).firstOrNull()?.let {
            strategy.createTree().apply {
                root = it.root?.deserialize()
            }
        }

    override fun set(treeName: String, tree: TreeType): Unit {
        remove(treeName) // remove first if already exists

        session.save(
            GraphTree(
                name = treeName,
                type = bstType,
                root = tree.root?.toGraphNode(),
            )
        )
    }

    override fun remove(treeName: String): Boolean =
        session.query(
            "MATCH p=(tree:Tree{name: \$name, type: \$type})-[*0..]->() DELETE p",
            mapOf("name" to treeName, "type" to bstType)
        ).queryStatistics().containsUpdates()


    private fun NodeType.toGraphNode(): GraphNode = GraphNode(
        data = strategy.collectData(this),
        metadata = strategy.collectMetadata(this),
        left = left?.toGraphNode(),
        right = right?.toGraphNode()
    )

    private fun GraphNode.deserialize(parent: NodeType? = null): NodeType {
        val node = strategy.createNode(data)
        strategy.processMetadata(node, metadata)

        node.parent = parent
        node.left = left?.deserialize(node)
        node.right = right?.deserialize(node)

        return node
    }
}
