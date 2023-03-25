package bstrees

import bstrees.nodes.TreeNode
import bstrees.balancers.TreeBalancer

abstract class BinarySearchTree<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    protected var treeRoot: NodeType? = null

    fun search(data: T): T? = searchNode(data)?.data
    protected fun searchNode(data: T): NodeType? {
        // searches for node and returns it as a result (or null)
        // aside from public 'search' method
        // it might be useful for deletion implementations to find the node to delete
        // that's why we're keeping it visible to inherited classes
        TODO("Not yet implemented")
    }

    open fun insert(data: T) {
        insertNode(data)
    }

    protected abstract fun createNewNode(data: T): NodeType
    protected fun insertNode(data: T): NodeType {
        // does simple insert and returns inserted node
        // uses 'createNewNode' to create new node to insert
        // will be used in different implementations of trees as part of the insert process
        TODO("Not yet implemented")
    }

    abstract fun delete(data: T): T?
}

abstract class SelfBalancingBST<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> : BinarySearchTree<T, NodeType>() {
    protected abstract val balancer: TreeBalancer<T, NodeType>
}
