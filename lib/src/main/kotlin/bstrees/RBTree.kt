package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer
import bstrees.nodes.RBNode.Color.Red
import bstrees.nodes.RBNode.Color.Black

class RBTree<E : Comparable<E>> : SelfBalancingBST<E, RBNode<E>>() {
    override fun createNewNode(data: E) = RBNode(data)
    override val balancer = RBBalancer<E>()

    override fun insert(data: E) {
        // insert like in SimpleBST and paint the new Node red
        val currentNode = insertNode(data) ?: return
        currentNode.color = Red
        root = balancer.balanceAfterInsertion(currentNode)
    }

    override fun delete(data: E): E? {
        val foundNode = searchNode(data) ?: return null
        val result = foundNode.data

        val nodeToDelete = if (foundNode.left != null && foundNode.right != null) {
            val nodeToReplaceWith = findPredecessor(foundNode)
            foundNode.data = nodeToReplaceWith.data
            nodeToReplaceWith
        } else {
            foundNode
        }

        //nodeToDelete has one child or zero
        when {
            // node is the leaf
            nodeToDelete.left == null && nodeToDelete.right == null -> {
                if (nodeToDelete.color == Black) {
                    root = balancer.balanceAfterDeletion(nodeToDelete)
                }
                replaceChild(nodeToDelete, null)
            }

            // node has only left child
            nodeToDelete.left != null -> {
                nodeToDelete.left?.flipColor()
                replaceChild(nodeToDelete, nodeToDelete.left)
            }

            // node has only right child
            else -> {
                nodeToDelete.right?.flipColor()
                replaceChild(nodeToDelete, nodeToDelete.right)
            }
        }

        return result
    }
}
