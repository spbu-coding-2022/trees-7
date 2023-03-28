package bstrees

import bstrees.nodes.TreeNode
import bstrees.balancers.TreeBalancer

abstract class BinarySearchTree<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    protected var treeRoot: NodeType? = null

    fun search(data: T): T? = searchNode(data)?.data

    /**
     * Searches for node and returns it as a result (or null).
     */
    protected fun searchNode(data: T): NodeType? {

        var tmpNode = treeRoot
        while (tmpNode != null) {
            val res = data.compareTo(tmpNode.data)
            if (res < 0) tmpNode = tmpNode.left
            else if (res > 0) tmpNode = tmpNode.right
            else return tmpNode
        }
        return null
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
