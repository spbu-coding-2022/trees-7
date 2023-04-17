package bstrees.repos.strategies

import bstrees.SimpleBST
import bstrees.nodes.SimpleNode

/**
 * Used to serialize and deserialize [SimpleBST].
 *
 * To serialize and deserialize tree's data type [T]
 * strategy must be provided with corresponding functions.
 */
class SimpleStrategy<T : Comparable<T>>(
    serializeData: (T) -> String, deserializeData: (String) -> T
) : SerializationStrategy<T, SimpleNode<T>, SimpleBST<T>>(serializeData, deserializeData) {
    override val bstType = BSTType.Simple

    override fun createTree() = SimpleBST<T>()
    override fun createNode(data: T) = SimpleNode(data)

    override fun collectMetadata(node: SimpleNode<T>) = ""
    override fun processMetadata(node: SimpleNode<T>, metadata: String) {}
}
