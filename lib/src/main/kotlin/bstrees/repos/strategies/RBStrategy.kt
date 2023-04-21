package bstrees.repos.strategies

import bstrees.RBTree
import bstrees.nodes.RBNode

/**
 * Used to serialize and deserialize [RBTree].
 *
 * To serialize and deserialize tree's data type [T]
 * strategy must be provided with corresponding functions.
 */
class RBStrategy<T : Comparable<T>>(
    serializeData: (T) -> String, deserializeData: (String) -> T
) : SerializationStrategy<T, RBNode<T>, RBTree<T>>(serializeData, deserializeData) {
    override val bstType = BSTType.RB

    override fun createNode(data: T) = RBNode(data)
    override fun createTree() = RBTree<T>()

    override fun collectMetadata(node: RBNode<T>) = when (node.color) {
        RBNode.Color.Red -> "R"
        RBNode.Color.Black -> "B"
    }

    override fun processMetadata(node: RBNode<T>, metadata: String) {
        when (metadata) {
            "R" -> node.color = RBNode.Color.Red
            "B" -> node.color = RBNode.Color.Black
            else -> throw IllegalArgumentException("Metadata must contain node's color")
        }
    }
}

