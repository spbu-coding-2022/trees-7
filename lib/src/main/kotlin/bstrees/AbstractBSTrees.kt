package bstrees

import bstrees.nodes.TreeNode
import bstrees.balancers.TreeBalancer

abstract class BinarySearchTree<E : Comparable<E>, N : TreeNode<E, N>> {
    var root: N? = null
        internal set

    /** Searches [data] in the tree. Returns it if found */
    fun search(data: E): E? = searchNode(data)?.data

    /** Searches for node and returns it as a result (or null) */
    protected fun searchNode(data: E): N? {
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
    open fun insert(data: E) {
        insertNode(data)
    }

    protected abstract fun createNewNode(data: E): N

    /**
     * Does simple insert and returns inserted node.
     * Returns null and overwrites the data if a node with that data already exists.
     * Uses [createNewNode] to create new node to insert
     */
    protected fun insertNode(data: E): N? {
        if (root == null) {
            val createdNode = createNewNode(data)
            root = createdNode
            return createdNode
        }

        var currentNode = root
            ?: throw IllegalStateException("Case when the root is null is processed above")
        while (true) {
            val res = data.compareTo(currentNode.data)
            if (res < 0) {
                if (currentNode.left == null) {
                    val createdNode = createNewNode(data)
                    currentNode.left = createdNode
                    createdNode.parent = currentNode
                    return createdNode
                }
                currentNode = currentNode.left
                    ?: throw IllegalStateException("Case when the left child of the currentNode is null is processed above")
            } else if (res > 0) {
                if (currentNode.right == null) {
                    val createdNode = createNewNode(data)
                    currentNode.right = createdNode
                    createdNode.parent = currentNode
                    return createdNode
                }
                currentNode = currentNode.right
                    ?: throw IllegalStateException("Case when the right child of the currentNode is null is processed above")
            } else {
                currentNode.data = data
                return null
            }
        }
    }

    /** Deletes node with [data] as its value. Returns deleted data */
    open fun delete(data: E): E? {
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
    protected fun deleteNode(node: N): N {
        return when {
            node.left == null && node.right == null ->
                deleteLeafNode(node)

            node.left == null || node.right == null ->
                deleteNodeWithOneChild(node)

            else -> deleteNodeWithTwoChildren(node)
        }
    }

    /** Replaces the child of the parent of the [wasChild] to [newChild] */
    protected fun replaceChild(wasChild: N, newChild: N?) {
        val parent = wasChild.parent
        if (parent == null) {
            root = newChild
        } else if (parent.left == wasChild) {
            parent.left = newChild
        } else {
            parent.right = newChild
        }

        newChild?.parent = wasChild.parent
    }

    /** Searches for node's in-order predecessor */
    protected fun findPredecessor(node: N): N {
        var nodeToReplaceWith = node.left
            ?: throw IllegalStateException("node must have two children")
        while (nodeToReplaceWith.right != null) {
            nodeToReplaceWith = nodeToReplaceWith.right
                ?: throw IllegalStateException("nodeToReplaceWith must have right child")
        }
        return nodeToReplaceWith
    }

    /** The node to be deleted is a leaf node. Returns deleted node */
    private fun deleteLeafNode(node: N): N {
        replaceChild(node, null)
        return node
    }

    /** The node to be deleted has only one child. Returns deleted node */
    private fun deleteNodeWithOneChild(node: N): N {
        val nodeToReplaceWith = if (node.left == null) node.right else node.left
        replaceChild(node, nodeToReplaceWith)
        return node
    }


    /**
     * The node to be deleted has two children.
     * Returns node that was actually deleted
     */
    private fun deleteNodeWithTwoChildren(node: N): N {
        val nodePredecessor = findPredecessor(node)
        // replace data and delete predecessor
        node.data = nodePredecessor.data
        return deleteNode(nodePredecessor)
    }

}

abstract class SelfBalancingBST<E : Comparable<E>, N : TreeNode<E, N>> :
    BinarySearchTree<E, N>() {
    internal abstract val balancer: TreeBalancer<E, N>
}
