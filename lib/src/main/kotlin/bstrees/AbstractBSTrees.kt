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
        var currentNode = treeRoot
        while (currentNode != null) {
            val res = data.compareTo(currentNode.data)
            currentNode = when {
                res < 0 -> currentNode.left
                res > 0 -> currentNode.right
                else -> return currentNode
            }
        }
        return null
    }

    open fun insert(data: T) {
        insertNode(data)
    }

    protected abstract fun createNewNode(data: T): NodeType

    /**
     * Does simple insert and returns inserted node
     * Uses [createNewNode] to create new node to insert
     */
    protected fun insertNode(data: T): NodeType {

        if (treeRoot == null) {
            val createdNode = createNewNode(data)
            treeRoot = createdNode
            return createdNode
        }

        var currentNode = treeRoot!!
        while (true) {
            val res = data.compareTo(currentNode.data)
            if (res < 0) {
                if (currentNode.left == null) {
                    val createdNode = createNewNode(data)
                    currentNode.left = createdNode
                    createdNode.parent = currentNode
                    return createdNode
                }
                currentNode = currentNode.left!!
            } else if (res > 0) {
                if (currentNode.right == null) {
                    val createdNode = createNewNode(data)
                    currentNode.right = createdNode
                    createdNode.parent = currentNode
                    return createdNode
                }
                currentNode = currentNode.right!!
            } else {
                currentNode.data = data
                return currentNode
            }
        }
    }

    abstract fun delete(data: T): T?
}

abstract class SelfBalancingBST<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> :
        BinarySearchTree<T, NodeType>() {
    protected abstract val balancer: TreeBalancer<T, NodeType>
}
