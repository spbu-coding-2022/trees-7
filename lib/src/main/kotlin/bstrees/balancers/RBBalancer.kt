package bstrees.balancers

import bstrees.nodes.RBNode

class RBBalancer<T : Comparable<T>> : TreeBalancer<T, RBNode<T>> {
    private fun getColor(node: RBNode<T>?) = node?.color ?: RBNode.Color.Black

    private fun isRed(node: RBNode<T>?) = getColor(node) == RBNode.Color.Red

    private fun isBlack(node: RBNode<T>?) = !isRed(node)

    private fun getSibling(node: RBNode<T>): RBNode<T>? {
        val parent = node.parent ?: return null
        return if (parent.left == node) parent.right
        else parent.left
    }

    private fun getUncle(node: RBNode<T>): RBNode<T>? {
        val parent = node.parent ?: return null
        return getSibling(parent)
    }

    private fun rotateRight(node: RBNode<T>): RBNode<T> {
        val parent = node.parent
        val wasChild = node.left
            ?: throw IllegalArgumentException("Node to rotate must have a left child")
        node.left = wasChild.right
        wasChild.right = node
        node.left?.parent = node
        node.parent = wasChild

        if (parent?.left == node) parent.left = wasChild else parent?.right = wasChild
        wasChild.parent = parent

        return wasChild
    }

    private fun rotateLeft(node: RBNode<T>): RBNode<T> {
        val parent = node.parent
        val wasChild = node.right
            ?: throw IllegalArgumentException("Node to rotate must have a right child")
        node.right = wasChild.left
        wasChild.left = node
        node.right?.parent = node
        node.parent = wasChild

        if (parent?.left == node) parent.left = wasChild else parent?.right = wasChild
        wasChild.parent = parent

        return wasChild
    }

    /** Accepts the inserted node. Returns new tree root */
    override fun balanceAfterInsertion(node: RBNode<T>): RBNode<T> {
        var currentNode = node
        while (isRed(currentNode)) {
            val parent = currentNode.parent
            if (parent == null) {
                currentNode.flipColor()
                break
            }

            val uncle = getUncle(currentNode)
            val grandParent = parent.parent
            if (isRed(uncle)) {
                // here parent, uncle and grandParent can not be null
                parent.flipColor()
                uncle!!.flipColor()
                grandParent!!.flipColor()
                currentNode = grandParent
            } else {
                if (currentNode == parent.right) {
                    rotateLeft(parent)
                }
                currentNode = rotateRight(grandParent!!)
                currentNode.flipColor()
                currentNode.right?.flipColor()
            }
        }
        return currentNode
    }

    override fun balanceAfterDeletion(node: RBNode<T>): RBNode<T> {
        TODO("Not yet implemented")
    }
}
