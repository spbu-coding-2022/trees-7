package bstrees.balancers

import bstrees.nodes.RBNode

internal class RBBalancer<T : Comparable<T>> : TreeBalancer<T, RBNode<T>> {
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

    /**
     * Accepts the node to be deleted. Returns new tree root.
     *
     * Doesn't itself delete [node]. Caller must delete the node on their own
     */
    override fun balanceAfterDeletion(node: RBNode<T>): RBNode<T> {
        var currentNode = node
        while (currentNode.parent != null && isBlack(currentNode)) {
            val parent = currentNode.parent
            if (parent?.left == currentNode) {
                val rightChild = parent.right
                if (isRed(parent)) {
                    // here rightChild must be black
                    if (isRed(rightChild?.left) || isRed(rightChild?.right)) {
                        //rightChild has at least one red child
                        parent.flipColor()
                        if (isRed(rightChild?.left)) {
                            rotateRight(rightChild!!)
                        } else {
                            rightChild?.flipColor()
                            rightChild?.right?.flipColor()
                        }
                        currentNode = rotateLeft(parent)
                    } else {
                        parent.flipColor()
                        rightChild?.flipColor()
                    }
                    break
                } else {
                    if (isRed(rightChild)) {
                        // here leftChild and leftChild.right can not be null
                        var grandChild = rightChild?.left
                        if (isRed(grandChild?.left) || isRed(grandChild?.right)) {
                            if (isBlack(grandChild?.right)) {
                                grandChild?.flipColor()
                                grandChild?.left?.flipColor()
                                grandChild = rotateRight(grandChild!!)
                            }
                            grandChild?.right?.flipColor()
                            rotateRight(rightChild!!)
                        } else {
                            rightChild?.flipColor()
                            grandChild?.flipColor()
                        }
                        currentNode = rotateLeft(parent)
                        break
                    } else {
                        if (isRed(rightChild?.left) || isRed(rightChild?.right)) {
                            //rightChild has at least one red child
                            if (isRed(rightChild?.left)) {
                                rightChild?.left?.flipColor()
                                rotateRight(rightChild!!)
                            } else {
                                rightChild?.right?.flipColor()
                            }
                            currentNode = rotateLeft(parent)
                            break
                        } else {
                            // here rightChild can not be null
                            rightChild?.flipColor()
                            currentNode = parent
                        }
                    }
                }
            } else {
                val leftChild = parent?.left
                if (isRed(parent)) {
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
                        currentNode = rotateRight(parent!!)
                    } else {
                        parent?.flipColor()
                        leftChild?.flipColor()
                    }
                    break
                } else {
                    if (isRed(leftChild)) {
                        // here leftChild and leftChild.right can not be null
                        var grandChild = leftChild?.right
                        if (isRed(grandChild?.left) || isRed(grandChild?.right)) {
                            if (isBlack(grandChild?.left)) {
                                grandChild?.flipColor()
                                grandChild?.right?.flipColor()
                                grandChild = rotateLeft(grandChild!!)
                            }
                            grandChild?.left?.flipColor()
                            rotateLeft(leftChild!!)
                        } else {
                            leftChild?.flipColor()
                            grandChild?.flipColor()
                        }
                        currentNode = rotateRight(parent!!)
                        break
                    } else {
                        if (isRed(leftChild?.left) || isRed(leftChild?.right)) {
                            //leftChild has at least one red child
                            if (isRed(leftChild?.right)) {
                                leftChild?.right?.flipColor()
                                rotateLeft(leftChild!!)
                            } else {
                                leftChild?.left?.flipColor()
                            }
                            currentNode = rotateRight(parent!!)
                            break
                        } else {
                            // here leftChild can not be null
                            leftChild?.flipColor()
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
