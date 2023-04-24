package bstrees.repos.strategies

import bstrees.RBTree
import bstrees.nodes.RBNode

/**
 * Used to serialize and deserialize [RBTree].
 *
 * To serialize and deserialize tree's data type [E]
 * strategy must be provided with corresponding functions.
 */
class RBStrategy<E : Comparable<E>>(
    serializeData: (E) -> String, deserializeData: (String) -> E
) : SerializationStrategy<E, RBNode<E>, RBTree<E>>(serializeData, deserializeData) {
    override val bstType = BSTType.RB

    override fun createNode(data: E) = RBNode(data)
    override fun createTree() = RBTree<E>()

    override fun collectMetadata(node: RBNode<E>) = when (node.color) {
        RBNode.Color.Red -> "R"
        RBNode.Color.Black -> "B"
    }

    override fun processMetadata(node: RBNode<E>, metadata: String) {
        when (metadata) {
            "R" -> node.color = RBNode.Color.Red
            "B" -> node.color = RBNode.Color.Black
            else -> throw IllegalArgumentException("Metadata must contain node's color")
        }
    }
}

