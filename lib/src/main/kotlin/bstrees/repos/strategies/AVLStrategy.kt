package bstrees.repos.strategies

import bstrees.AVLTree
import bstrees.nodes.AVLNode

/**
 * Used to serialize and deserialize [AVLTree].
 *
 * To serialize and deserialize tree's data type [E]
 * strategy must be provided with corresponding functions.
 */
class AVLStrategy<E : Comparable<E>>(
    serializeData: (E) -> String, deserializeData: (String) -> E
) : SerializationStrategy<E, AVLNode<E>, AVLTree<E>>(serializeData, deserializeData) {
    override val bstType = BSTType.AVL

    override fun createNode(data: E) = AVLNode(data)
    override fun createTree() = AVLTree<E>()

    override fun collectMetadata(node: AVLNode<E>) = node.height.toString()
    override fun processMetadata(node: AVLNode<E>, metadata: String) {
        node.height = metadata.toIntOrNull()
            ?: throw IllegalArgumentException("Metadata must contain node's height")
    }
}
