package bstrees.balancers

import bstrees.nodes.RBNode

internal class RBBalancer<E : Comparable<E>> : TreeBalancer<E, RBNode<E>>() {
    private fun getColor(node: RBNode<E>?) = node?.color ?: RBNode.Color.Black

    private fun isRed(node: RBNode<E>?) = getColor(node) == RBNode.Color.Red

    private fun isBlack(node: RBNode<E>?) = !isRed(node)

    private fun getSibling(node: RBNode<E>): RBNode<E>? {
        val parent = node.parent ?: return null
        return if (parent.left == node) parent.right
        else parent.left
    }

    private fun getUncle(node: RBNode<E>): RBNode<E>? {
        val parent = node.parent ?: return null
        return getSibling(parent)
    }

    /** Accepts the inserted node. Returns new tree root */
    override fun balanceAfterInsertion(node: RBNode<E>): RBNode<E> {
        var currentNode = node

        while (currentNode.parent != null && isRed(currentNode.parent)) {
            val parent = currentNode.parent
                ?: throw IllegalStateException("parent can not be null")
            val grandParent = parent.parent
                ?: throw IllegalStateException("grandParent can not be null")
            val uncle = getUncle(currentNode)

            if (isRed(uncle)) {
                // here parent, uncle and grandParent can not be null
                parent.flipColor()
                grandParent.flipColor()
                uncle?.flipColor()
                currentNode = grandParent
            } else {
                if (grandParent.left == parent) {
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
            currentNode = currentNode.parent
                ?: throw IllegalStateException("currentNode must have parent")
        }

        return currentNode
    }

    /**
     * Accepts the node to be deleted. Returns new tree root.
     *
     * Doesn't itself delete [node]. Caller must delete the node on their own
     */
    override fun balanceAfterDeletion(node: RBNode<E>): RBNode<E> {
        var currentNode = node
        while (currentNode.parent != null && isBlack(currentNode)) {
            val parent = currentNode.parent
                ?: throw IllegalStateException("parent can not be null")
            if (parent.left == currentNode) {
                val rightChild = parent.right
                    ?: throw IllegalStateException("parent must have right child")
                if (isRed(parent)) {
                    // here rightChild must be black
                    if (isRed(rightChild.left) || isRed(rightChild.right)) {
                        //rightChild has at least one red child
                        parent.flipColor()
                        if (isRed(rightChild.left)) {
                            rotateRight(rightChild)
                        } else {
                            rightChild.flipColor()
                            rightChild.right?.flipColor()
                        }
                        currentNode = rotateLeft(parent)
                    } else {
                        //rightChild has not any red children
                        parent.flipColor()
                        rightChild.flipColor()
                    }
                    break
                } else {
                    if (isRed(rightChild)) {
                        var grandChild = rightChild.left
                            ?: throw IllegalStateException("grandChild can not be null")
                        //grandChild must be black
                        if (isRed(grandChild.left) || isRed(grandChild.right)) {
                            //grandChild has at least one red child
                            if (isBlack(grandChild.right)) {
                                grandChild.flipColor()
                                grandChild.left?.flipColor()

                                grandChild = rotateRight(grandChild)
                            }
                            grandChild.right?.flipColor()
                            rotateRight(rightChild)
                        } else {
                            //grandChild has not any red children
                            rightChild.flipColor()
                            grandChild.flipColor()
                        }
                        currentNode = rotateLeft(parent)
                        break
                    } else {
                        if (isRed(rightChild.left) || isRed(rightChild.right)) {
                            //rightChild has at least one red child
                            if (isRed(rightChild.left)) {
                                rightChild.left?.flipColor()
                                rotateRight(rightChild)
                            } else {
                                rightChild.right?.flipColor()
                            }
                            currentNode = rotateLeft(parent)
                            break
                        } else {
                            //rightChild has not any red children
                            rightChild.flipColor()
                            currentNode = parent
                        }
                    }
                }
            } else {
                val leftChild = parent.left
                    ?: throw IllegalStateException("parent must have left child")
                if (isRed(parent)) {
                    // here leftChild must be black
                    if (isRed(leftChild.left) || isRed(leftChild.right)) {
                        //leftChild has at least one red child
                        parent.flipColor()
                        if (isRed(leftChild.right)) {
                            rotateLeft(leftChild)
                        } else {
                            leftChild.flipColor()
                            leftChild.left?.flipColor()
                        }
                        currentNode = rotateRight(parent)
                    } else {
                        //leftChild has not any red children
                        parent.flipColor()
                        leftChild.flipColor()
                    }
                    break
                } else {
                    if (isRed(leftChild)) {
                        var grandChild = leftChild.right
                            ?: throw IllegalStateException("grandChild can not be null")
                        //grandChild must be black
                        if (isRed(grandChild.left) || isRed(grandChild.right)) {
                            //grandChild has at least one red child
                            if (isBlack(grandChild.left)) {
                                grandChild.flipColor()
                                grandChild.right?.flipColor()
                                grandChild = rotateLeft(grandChild)
                            }
                            grandChild.left?.flipColor()
                            rotateLeft(leftChild)
                        } else {
                            //grandChild has not any red children
                            leftChild.flipColor()
                            grandChild.flipColor()
                        }
                        currentNode = rotateRight(parent)
                        break
                    } else {
                        if (isRed(leftChild.left) || isRed(leftChild.right)) {
                            //leftChild has at least one red child
                            if (isRed(leftChild.right)) {
                                leftChild.right?.flipColor()
                                rotateLeft(leftChild)
                            } else {
                                leftChild.left?.flipColor()
                            }
                            currentNode = rotateRight(parent)
                            break
                        } else {
                            //leftChild has not any red children
                            leftChild.flipColor()
                            currentNode = parent
                        }
                    }
                }
            }
        }

        // go to root
        while (currentNode.parent != null) {
            currentNode = currentNode.parent
                ?: throw IllegalStateException("currentNode must have parent")
        }

        return currentNode
    }
}
