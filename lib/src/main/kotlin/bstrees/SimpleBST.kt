package bstrees

import bstrees.nodes.SimpleNode

class SimpleBST<T : Comparable<T>> : BinarySearchTree<T, SimpleNode<T>>() {
    override fun createNewNode(data: T) = SimpleNode(data)

    /** Deletes node and returns it as a result (or null). */
    override fun delete(data: T): T? {
        val nodeToDelete = searchNodeForDelete(data)

        if (nodeToDelete == null) return null
        else {
            when {
                nodeToDelete.left == null && nodeToDelete.right == null -> deleteLeafNode(nodeToDelete)
                nodeToDelete.left == null || nodeToDelete.right == null -> deleteNodeWithOneChild(nodeToDelete)
                else -> deleteNodeWithTwoChildren(nodeToDelete)
            }
        }
        return data
    }

    /** Searches for node to delete. Returns null if node is not found */
    private fun searchNodeForDelete(data: T): SimpleNode<T>? = searchNode(data)

    /** The node to be deleted is a leaf node */
    private fun deleteLeafNode(node: SimpleNode<T>) {
        val parent = node.parent
        if (parent != null) {
            if (parent.left == node) parent.left = null
            if (parent.right == node) parent.right = null
        } else treeRoot = null
    }

    /** The node to be deleted has only one child. */
    private fun deleteNodeWithOneChild(node: SimpleNode<T>) {
        val parent = node.parent

        if (parent == null) {
            treeRoot = node.left ?: node.right
            treeRoot?.parent = null
        } else {
            val nodeToReplaceWith = if (node.left == null) node.right!! else node.left!!
            if (node == parent.right) parent.right = nodeToReplaceWith
            else parent.left = nodeToReplaceWith

            nodeToReplaceWith.parent = parent
        }
    }

    /** Searches for node's successor until the node value is placed on the leaf of the tree. */
    private fun findNodeToReplaceWith(node: SimpleNode<T>): SimpleNode<T> {
        var nodeToReplaceWith = node
        while (nodeToReplaceWith.right != null) {
            nodeToReplaceWith = nodeToReplaceWith.right!!;
        }
        return nodeToReplaceWith;
    }

    /** The node to be deleted has two children. */
    private fun deleteNodeWithTwoChildren(node: SimpleNode<T>) {
        var nodeToDelete = node
        val nodeToReplaceWith = findNodeToReplaceWith(node.left!!)

        nodeToDelete.data = nodeToReplaceWith.data
        nodeToDelete = nodeToReplaceWith

        if (nodeToReplaceWith.left == null && nodeToReplaceWith.right == null) deleteLeafNode(nodeToReplaceWith)
        else deleteNodeWithOneChild(nodeToReplaceWith)
    }
}
