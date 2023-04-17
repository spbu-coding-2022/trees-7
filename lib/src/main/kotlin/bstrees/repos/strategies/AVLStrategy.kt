package bstrees.repos.strategies

import bstrees.AVLTree
import bstrees.nodes.AVLNode

/**
 * Used to serialize and deserialize [AVLTree].
 *
 * To serialize and deserialize tree's data type [T]
 * strategy must be provided with corresponding functions.
 */
class AVLStrategy<T : Comparable<T>>(
    serializeData: (T) -> String, deserializeData: (String) -> T
) : SerializationStrategy<T, AVLNode<T>, AVLTree<T>>(serializeData, deserializeData) {
    override val bstType = BSTType.AVL

    override fun createNode(data: T) = AVLNode(data)
    override fun createTree() = AVLTree<T>()

    override fun collectMetadata(node: AVLNode<T>) = node.height.toString()
    override fun processMetadata(node: AVLNode<T>, metadata: String) {
        node.height = metadata.toIntOrNull()
            ?: throw IllegalArgumentException("Metadata must contain node's height")
    }
}
