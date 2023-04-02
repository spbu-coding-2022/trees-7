package bstrees

import bstrees.nodes.AVLNode
import bstrees.balancers.AVLBalancer
import bstrees.wrapped.WrappedAVLNode

class AVLTree<T : Comparable<T>> : SelfBalancingBST<T, AVLNode<T>, WrappedAVLNode<T>>() {
    override val root get() = treeRoot?.let { WrappedAVLNode(it) }

    override fun createNewNode(data: T) = AVLNode(data)
    override val balancer = AVLBalancer<T>()

    /** Inserts new node with [data] as its value in the tree */
    override fun insert(data: T) {
        val insertedNode = insertNode(data)
        treeRoot = balancer.balanceAfterInsertion(insertedNode)
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
