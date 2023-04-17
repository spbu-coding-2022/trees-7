package bstrees.repos.strategies

import bstrees.BinarySearchTree
import bstrees.nodes.TreeNode

enum class BSTType {
    Simple,
    AVL,
    RB
}

abstract class SerializationStrategy<T : Comparable<T>,
        NodeType : TreeNode<T, NodeType>,
        TreeType : BinarySearchTree<T, NodeType>>(
    private val serializeData: (T) -> String,
    private val deserializeData: (String) -> T
) {
    abstract val bstType: BSTType

    fun createNode(data: String): NodeType = createNode(deserializeData(data))
    protected abstract fun createNode(data: T): NodeType
    abstract fun createTree(): TreeType

    fun collectData(node: NodeType): String = serializeData(node.data)
    abstract fun collectMetadata(node: NodeType): String
    abstract fun processMetadata(node: NodeType, metadata: String)
}
