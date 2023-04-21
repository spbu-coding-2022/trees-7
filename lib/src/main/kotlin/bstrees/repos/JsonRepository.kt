package bstrees.repos

import bstrees.BinarySearchTree
import bstrees.nodes.TreeNode
import bstrees.repos.strategies.SerializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException


@Serializable
private data class JsonNode(
    val data: String,
    val metadata: String,
    val left: JsonNode?,
    val right: JsonNode?
)

@Serializable
private data class JsonTree(
    val name: String,
    val root: JsonNode?
)


/**
 * Saves binary search trees as .json files in provided [dirPath].
 * Acts like associative array with trees' names as keys and trees as values.
 *
 * Must be provided with [strategy] so repository knows how to work with specific [TreeType].
 *
 * Different tree types can be saved in the same [dirPath]
 * by creating several JsonRepositories with same [dirPath].
 * Note that saving trees with same tree type but different data type [T]
 * in the same [dirPath] is error-prone and is not advised.
 */
class JsonRepository<T : Comparable<T>,
        NodeType : TreeNode<T, NodeType>,
        TreeType : BinarySearchTree<T, NodeType>>(
    private val strategy: SerializationStrategy<T, NodeType, TreeType>, dirPath: String
) : TreeRepository<TreeType> {
    private val dirPath = "$dirPath/${strategy.bstType.toString().lowercase()}"

    // uses hash codes of trees' names as filenames
    // as storing files with arbitrary unicode names can be error-prone

    override val names: List<String>
        get() = File(dirPath).listFiles()?.map {
            Json.decodeFromString<JsonTree>(it.readText()).name
        } ?: listOf()

    override fun get(treeName: String): TreeType? {
        val json = try {
            File(dirPath, "${treeName.hashCode()}.json").readText()
        } catch (_: FileNotFoundException) {
            return null
        }

        val jsonTree = Json.decodeFromString<JsonTree>(json)
        return strategy.createTree().apply {
            root = jsonTree.root?.deserialize()
        }
    }

    override fun set(treeName: String, tree: TreeType) {
        val jsonTree = JsonTree(treeName, tree.root?.toJsonNode())

        File(dirPath).mkdirs()
        File(dirPath, "${treeName.hashCode()}.json").run {
            createNewFile()
            writeText(Json.encodeToString(jsonTree))
        }
    }

    override fun remove(treeName: String): Boolean =
        File(dirPath, "${treeName.hashCode()}.json").delete()


    private fun NodeType.toJsonNode(): JsonNode = JsonNode(
        data = strategy.collectData(this),
        metadata = strategy.collectMetadata(this),
        left = left?.toJsonNode(),
        right = right?.toJsonNode()
    )

    private fun JsonNode.deserialize(parent: NodeType? = null): NodeType {
        val node = strategy.createNode(data)
        strategy.processMetadata(node, metadata)

        node.parent = parent
        node.left = left?.deserialize(node)
        node.right = right?.deserialize(node)

        return node
    }
}
