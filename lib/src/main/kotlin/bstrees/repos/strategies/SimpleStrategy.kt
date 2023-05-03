package bstrees.repos.strategies

import bstrees.SimpleBST
import bstrees.nodes.SimpleNode

/**
 * Used to serialize and deserialize [SimpleBST].
 *
 * To serialize and deserialize tree's data type [E]
 * strategy must be provided with corresponding functions.
 */
class SimpleStrategy<E : Comparable<E>>(
    serializeData: (E) -> String, deserializeData: (String) -> E
) : SerializationStrategy<E, SimpleNode<E>, SimpleBST<E>>(serializeData, deserializeData) {
    override val bstType = BSTType.Simple

    override fun createTree() = SimpleBST<E>()
    override fun createNode(data: E) = SimpleNode(data)

    override fun collectMetadata(node: SimpleNode<E>) = ""
    override fun processMetadata(node: SimpleNode<E>, metadata: String) {}
}
