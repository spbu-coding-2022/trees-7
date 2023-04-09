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

        //nodeToDelete has one child or zero
        treeRoot = balancer.balanceAfterDeletion(nodeToDelete)

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
