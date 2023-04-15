package bstrees

import bstrees.nodes.TreeNode
import bstrees.balancers.TreeBalancer

abstract class BinarySearchTree<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    var root: NodeType? = null
        internal set

    /** Searches [data] in the tree. Returns it if found */
    fun search(data: T): T? = searchNode(data)?.data

    /** Searches for node and returns it as a result (or null) */
    protected fun searchNode(data: T): NodeType? {
        var currentNode = root
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

    /** Inserts new node in the tree with [data] as its value */
    open fun insert(data: T) {
        insertNode(data)
    }

    protected abstract fun createNewNode(data: T): NodeType

    /**
     * Does simple insert and returns inserted node.
     * Returns null and overwrites the data if a node with that data already exists.
     * Uses [createNewNode] to create new node to insert
     */
    protected fun insertNode(data: T): NodeType? {
        if (root == null) {
            val createdNode = createNewNode(data)
            root = createdNode
            return createdNode
        }

        var currentNode = root!!
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
                return null
            }
        }
    }

    /** Deletes node with [data] as its value. Returns deleted data */
    open fun delete(data: T): T? {
        val node = searchNode(data) ?: return null
        val dataToDelete = node.data

        deleteNode(node)
        return dataToDelete
    }

    /**
     * Deletes [node] from the tree.
     * Returns node that was actually deleted.
     * Note that returned node is not always the same as [node].
     * Returned node can have different data than [node]
     */
    protected fun deleteNode(node: NodeType): NodeType {
        return when {
            node.left == null && node.right == null ->
                deleteLeafNode(node)

            node.left == null || node.right == null ->
                deleteNodeWithOneChild(node)

            else -> deleteNodeWithTwoChildren(node)
        }
    }

    /** The node to be deleted is a leaf node. Returns deleted node */
    private fun deleteLeafNode(node: NodeType): NodeType {
        val parent = node.parent
        if (parent != null) {
            if (parent.right == node) parent.right = null
            else parent.left = null
        } else root = null
        return node
    }

    /** The node to be deleted has only one child. Returns deleted node */
    private fun deleteNodeWithOneChild(node: NodeType): NodeType {
        val parent = node.parent

        if (parent == null) {
            root = node.left ?: node.right
            root?.parent = null
        } else {
            val nodeToReplaceWith = if (node.left == null) node.right!! else node.left!!
            if (parent.right == node) parent.right = nodeToReplaceWith
            else parent.left = nodeToReplaceWith

            nodeToReplaceWith.parent = parent
        }
        return node
    }


    /**
     * The node to be deleted has two children.
     * Returns node that was actually deleted
     */
    private fun deleteNodeWithTwoChildren(node: NodeType): NodeType {
        // find in-order predecessor
        var nodeToReplaceWith = node.left!!
        while (nodeToReplaceWith.right != null)
            nodeToReplaceWith = nodeToReplaceWith.right!!

        // replace data and delete predecessor
        node.data = nodeToReplaceWith.data
        return deleteNode(nodeToReplaceWith)
    }

}

abstract class SelfBalancingBST<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> :
    BinarySearchTree<T, NodeType>() {
    protected abstract val balancer: TreeBalancer<T, NodeType>
}
