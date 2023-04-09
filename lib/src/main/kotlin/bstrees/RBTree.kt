package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer

class RBTree<T : Comparable<T>> : SelfBalancingBST<T, RBNode<T>>() {
    override fun createNewNode(data: T) = RBNode(data)
    override val balancer = RBBalancer<T>()

    /** Inserts a new node with [data] as its value in the tree */
    override fun insert(data: T) {
        // insert like in SimpleBST and paint the new Node red
        val currentNode = insertNode(data) ?: return
        currentNode.flipColor()
        root = balancer.balanceAfterInsertion(currentNode)
    }

    override fun delete(data: T): T? {
        val foundNode = searchNode(data) ?: return null
        val result = foundNode.data

        val nodeToDelete = if (foundNode.left != null && foundNode.right != null) {
            val nodeToReplaceWith = findNodeToReplaceWith(foundNode)
            foundNode.data = nodeToReplaceWith.data
            nodeToReplaceWith
        } else {
            foundNode
        }

        fun changeChild(wasChild: RBNode<T>, newChild: RBNode<T>?) {
            val parent = wasChild.parent
            if (parent == null) {
                root = newChild
            } else if (parent.left == wasChild) {
                parent.left = newChild
            } else {
                parent.right = newChild
            }

            newChild?.let { newChild.parent = wasChild.parent }
        }

        //nodeToDelete has one child or zero
        when {
            // node is the leaf
            nodeToDelete.left == null && nodeToDelete.right == null -> {
                // update children of the parent of the deleted node
                changeChild(nodeToDelete, null)

                if (nodeToDelete.color == RBNode.Color.Black) {
                    root = balancer.balanceAfterDeletion(nodeToDelete)
                }
            }

            nodeToDelete.left != null -> {
                nodeToDelete.left?.flipColor()
                changeChild(nodeToDelete, nodeToDelete.left)
            }

            else -> {
                nodeToDelete.right?.flipColor()
                changeChild(nodeToDelete, nodeToDelete.right)
            }
        }

        return result
    }

    /** Searches for node's successor until the node value is placed on the leaf of the tree. */
    private fun findNodeToReplaceWith(node: RBNode<T>): RBNode<T> {
        var nodeToReplaceWith = node.left!!
        while (nodeToReplaceWith.right != null) {
            nodeToReplaceWith = nodeToReplaceWith.right!!
        }
        return nodeToReplaceWith
    }
}
