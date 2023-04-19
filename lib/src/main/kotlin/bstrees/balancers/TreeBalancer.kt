package bstrees.balancers

import bstrees.nodes.TreeNode

internal abstract class TreeBalancer<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    abstract fun balanceAfterInsertion(node: NodeType): NodeType
    abstract fun balanceAfterDeletion(node: NodeType): NodeType

    /**
     * Rotates right edge of the [node] counterclockwise.
     * Returns node that will be in place of the [node] passed
     *
     * Throws [IllegalArgumentException] if [node] without right child is passed
     */
    protected open fun rotateLeft(node: NodeType): NodeType {
        val rightChild = node.right
            ?: throw IllegalArgumentException("Node to rotate must have a right child")

        rightChild.parent = node.parent
        if (node.parent?.left == node) node.parent?.left = rightChild
        else node.parent?.right = rightChild

        node.right = rightChild.left
        rightChild.left?.parent = node

        rightChild.left = node
        node.parent = rightChild

        return rightChild
    }

    /**
     * Rotates left edge of the [node] clockwise.
     * Returns node that will be in place of the [node] passed
     *
     * Throws [IllegalArgumentException] if [node] without left child is passed
     */
    protected open fun rotateRight(node: NodeType): NodeType {
        val leftChild = node.left
            ?: throw IllegalArgumentException("Node to rotate must have a left child")

        leftChild.parent = node.parent
        if (node.parent?.left == node) node.parent?.left = leftChild
        else node.parent?.right = leftChild

        node.left = leftChild.right
        leftChild.right?.parent = node

        leftChild.right = node
        node.parent = leftChild

        return leftChild
    }
}
