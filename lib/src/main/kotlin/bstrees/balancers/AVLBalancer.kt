package bstrees.balancers

import bstrees.nodes.AVLNode
import kotlin.math.max

internal class AVLBalancer<E : Comparable<E>> : TreeBalancer<E, AVLNode<E>>() {
    /**
     * Rotates right edge of the [node] counterclockwise.
     * Returns node that will be in place of the [node] passed
     *
     * Calls [updateHeight] to update heights of the nodes affected.
     * Throws [IllegalArgumentException] if [node] without right child is passed
     */
    override fun rotateLeft(node: AVLNode<E>): AVLNode<E> {
        val newRoot = super.rotateLeft(node)
        updateHeight(node)
        updateHeight(newRoot)
        return newRoot
    }

    /**
     * Rotates left edge of the [node] clockwise.
     * Returns node that will be in place of the [node] passed
     *
     * Calls [updateHeight] to update heights of the nodes affected.
     * Throws [IllegalArgumentException] if [node] without left child is passed
     */
    override fun rotateRight(node: AVLNode<E>): AVLNode<E> {
        val newRoot = super.rotateRight(node)
        updateHeight(node)
        updateHeight(newRoot)
        return newRoot
    }

    /** Returns height of the [node] in AVL tree. Returns 0 if null passed */
    private fun getHeight(node: AVLNode<E>?) = node?.height ?: 0

    /** Updates height of the [node] in AVL tree */
    private fun updateHeight(node: AVLNode<E>) {
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1
    }

    /** Returns balance factor of the [node] in AVL tree */
    private fun balanceFactor(node: AVLNode<E>) =
        getHeight(node.left) - getHeight(node.right)

    /**
     * Balances AVL tree after insertion of new element.
     * Must be called after every insertion with inserted [node] as parameter.
     * Returns new root of the tree
     */
    override tailrec fun balanceAfterInsertion(node: AVLNode<E>): AVLNode<E> {
        var currentNode = node
        when (balanceFactor(currentNode)) {
            // LL or LR
            2 -> currentNode.left?.let {
                when (balanceFactor(it)) {
                    // LL
                    1, 0 -> currentNode = rotateRight(currentNode)

                    // LR
                    -1 -> {
                        rotateLeft(it)
                        currentNode = rotateRight(currentNode)
                    }
                }
            }

            // RR or RL
            -2 -> currentNode.right?.let {
                when (balanceFactor(it)) {
                    // RR
                    -1, 0 -> currentNode = rotateLeft(currentNode)

                    // RL
                    1 -> {
                        rotateRight(it)
                        currentNode = rotateLeft(currentNode)
                    }
                }
            }

            else -> updateHeight(currentNode)
        }

        currentNode.parent?.let {
            return balanceAfterInsertion(it)
        }
        return currentNode
    }

    /**
     * Balances AVL tree after deletion of an element.
     * Must be called after every delete with deleted node's parent as parameter.
     * Note that deleted node is a node that doesn't belong to the tree.
     * Returns new root of the tree
     */
    override fun balanceAfterDeletion(node: AVLNode<E>) = balanceAfterInsertion(node)
}
