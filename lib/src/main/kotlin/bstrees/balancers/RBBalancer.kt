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

        while (currentNode.parent != null && isRed(currentNode.parent)) {
            val parent = currentNode.parent
            val grandParent = parent!!.parent
            val uncle = getUncle(currentNode)

            if (isRed(uncle)) {
                // here parent, uncle and grandParent can not be null
                parent.flipColor()
                uncle!!.flipColor()
                grandParent!!.flipColor()
                currentNode = grandParent
            } else {
                if (grandParent!!.left == parent) {
                    if (currentNode == parent.right) rotateLeft(parent)
                    currentNode = rotateRight(grandParent)
                    currentNode.right?.flipColor()
                } else {
                    if (currentNode == parent.left) rotateRight(parent)
                    currentNode = rotateLeft(grandParent)
                    currentNode.left?.flipColor()
                }
                currentNode.flipColor()
                break
            }
        }

        // fix root color if it was changed
        if (currentNode.parent == null && isRed(currentNode)) {
            currentNode.flipColor()
            return currentNode
        }

        // go to root
        while (currentNode.parent != null) {
            currentNode = currentNode.parent!!
        }

        return currentNode
    }

    /** Accepts the node to be deleted. Returns new tree root */
    override fun balanceAfterDeletion(node: RBNode<T>): RBNode<T> {
        var currentNode = node
        while (currentNode.parent != null && isBlack(currentNode)) {
            val parent = currentNode.parent
            if (parent?.left == currentNode) {
                TODO()
            } else {
                if (isRed(parent)) {
                    val leftChild = parent?.left
                    // here leftChild must be black
                    if (isRed(leftChild?.left) || isRed(leftChild?.right)) {
                        //leftChild has at least one red child
                        parent?.flipColor()
                        if (isRed(leftChild?.right)) {
                            rotateLeft(leftChild!!)
                        } else {
                            leftChild?.flipColor()
                            leftChild?.left?.flipColor()
                        }
                        rotateRight(parent!!)
                    } else {
                        parent?.flipColor()
                        leftChild?.flipColor()
                    }
                    break
                } else {
                    val leftChild = parent?.left
                    if (isRed(leftChild)) {
                        // here leftChild and leftChild.right can not be null
                        val grandChild = leftChild?.right
                        val redNode = when {
                            isRed(grandChild?.left) -> {
                                grandChild?.left?.flipColor()
                                grandChild?.left
                            }

                            isRed(grandChild?.right) -> grandChild?.right
                            else -> null
                        }
                        if (redNode != null) {
                            rotateLeft(leftChild!!)
                            rotateRight(parent)
                        } else {
                            leftChild?.flipColor()
                            grandChild?.flipColor()
                            rotateRight(parent!!)
                        }
                        break
                    } else {
                        if (isRed(leftChild?.left) || isRed(leftChild?.right)) {
                            //leftChild has at least one red child
                            parent?.flipColor()
                            if (isRed(leftChild?.right)) {
                                leftChild?.right?.flipColor()
                                rotateLeft(leftChild!!)
                            } else {
                                leftChild?.left?.flipColor()
                            }
                            rotateRight(parent!!)
                            break
                        } else {
                            // here leftChild can not be null
                            leftChild?.left?.flipColor()
                            currentNode = parent!!
                        }
                    }
                }
            }
        }

        // go to root
        while (currentNode.parent != null) {
            currentNode = currentNode.parent!!
        }

        return currentNode
    }
}
