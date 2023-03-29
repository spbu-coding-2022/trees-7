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

    /**
     * Does simple insert and returns inserted node
     * Uses 'createNewNode' to create new node to insert
     */
    protected fun insertNode(data: T): NodeType {
        val createdNode = createNewNode(data)

        if (treeRoot == null) {
            treeRoot = createdNode
            return createdNode
        }

        var tmpNode = treeRoot!!
        while (true) {
            val res = data.compareTo(tmpNode.data)
            if (res < 0) {
                if (tmpNode.left == null) {
                    tmpNode.left = createdNode
                    createdNode.parent = tmpNode
                    return createdNode
                }
                tmpNode = tmpNode.left!!
            }
            else if (res > 0) {
                if (tmpNode.right == null) {
                    tmpNode.right = createdNode
                    createdNode.parent = tmpNode
                    return createdNode
                }
                tmpNode = tmpNode.right!!
            }
            else return tmpNode
        }
    }

    abstract fun delete(data: T): T?
}

abstract class SelfBalancingBST<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> : BinarySearchTree<T, NodeType>() {
    protected abstract val balancer: TreeBalancer<T, NodeType>
}
