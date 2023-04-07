package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer

class RBTree<T : Comparable<T>> : SelfBalancingBST<T, RBNode<T>>() {
    override fun createNewNode(data: T) = RBNode(data)
    override val balancer = RBBalancer<T>()

    /** Inserts a new node with [data] as its value in the tree */
    override fun insert(data: T) {
        // inserts like in SimpleBST and paints the new Node red
        val currentNode = insertNode(data) ?: return
        currentNode.flipColor()
        root = balancer.balanceAfterInsertion(currentNode)
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
