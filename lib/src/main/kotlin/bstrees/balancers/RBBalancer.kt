package bstrees.balancers

import bstrees.nodes.RBNode

class RBBalancer<T : Comparable<T>> : TreeBalancer<T, RBNode<T>> {
    private fun color(node: RBNode<T>?) = node?.color ?: RBNode.Color.Black

    private fun isRed(node: RBNode<T>?) = color(node) == RBNode.Color.Red

    private fun isBlack(node: RBNode<T>?) = !isRed(node)

    private fun getParent(node: RBNode<T>) = node.parent

    private fun getSibling(node: RBNode<T>): RBNode<T>? {
        val parent = getParent(node) ?: return null
        return if (parent.left == node) parent.right
        else parent.left
    }

    private fun getUncle(node: RBNode<T>): RBNode<T>? {
        val parent = getParent(node) ?: return null
        return getSibling(parent)
    }

    private fun getGrandParent(node: RBNode<T>): RBNode<T>? = node.parent?.parent

    private fun rotateRight(node: RBNode<T>): RBNode<T> {
        val parent = getParent(node)
        val wasChild = node.left
        node.left = wasChild!!.right
        wasChild.right = node
        node.left?.parent = node
        node.parent = wasChild

        if (parent?.left == node) parent.left = wasChild else parent?.right = wasChild
        wasChild.parent = parent

        return wasChild
    }

    private fun rotateLeft(node: RBNode<T>): RBNode<T> {
        val parent = getParent(node)
        val wasChild = node.right
        node.right = wasChild!!.left
        wasChild.left = node
        node.right?.parent = node
        node.parent = wasChild

        if (parent?.left == node) parent.left = wasChild else parent?.right = wasChild
        wasChild.parent = parent

        return wasChild
    }

    /**
     * Returns new tree root (if it was change)
     */
    override fun balanceAfterInsertion(node: RBNode<T>) : RBNode<T> {
        var currentNode = node
        while (isRed(currentNode)) {
            val parent = getParent(currentNode)
            if (parent == null) {
                currentNode.flipColor()
                break
            }

            val uncle = getUncle(currentNode)
            val grandParent = getGrandParent(currentNode)
            if (isRed(uncle)) {
                // there parent, uncle and grandParent are not null
                parent.flipColor()
                uncle!!.flipColor()
                grandParent!!.flipColor()
                currentNode = grandParent
            }
            else {
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
