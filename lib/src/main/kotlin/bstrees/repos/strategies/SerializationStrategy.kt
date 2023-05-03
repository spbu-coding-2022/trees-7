package bstrees.repos.strategies

import bstrees.BinarySearchTree
import bstrees.nodes.TreeNode

enum class BSTType {
    Simple,
    AVL,
    RB
}

abstract class SerializationStrategy<E : Comparable<E>,
        N : TreeNode<E, N>,
        T : BinarySearchTree<E, N>>(
    private val serializeData: (E) -> String,
    private val deserializeData: (String) -> E
) {
    abstract val bstType: BSTType

    fun createNode(data: String): N = createNode(deserializeData(data))
    protected abstract fun createNode(data: E): N
    abstract fun createTree(): T

    fun collectData(node: N): String = serializeData(node.data)
    abstract fun collectMetadata(node: N): String
    abstract fun processMetadata(node: N, metadata: String)
}
