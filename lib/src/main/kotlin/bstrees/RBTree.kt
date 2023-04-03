package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer
import bstrees.wrapped.WrappedRBNode

class RBTree<T : Comparable<T>> : SelfBalancingBST<T, RBNode<T>, WrappedRBNode<T>>() {
    override val root get() = treeRoot?.let { WrappedRBNode(it) }

    override fun createNewNode(data: T) = RBNode(data)
    override val balancer = RBBalancer<T>()

    /** Inserts new node with [data] as its value in the tree */
    override fun insert(data: T) {
        // insert like in SimpleBST and paint the new Node red
        val currentNode = insertNode(data)
        currentNode.flipColor()
        treeRoot = balancer.balanceAfterInsertion(currentNode)
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
