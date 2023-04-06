package bstrees

import bstrees.nodes.AVLNode
import bstrees.balancers.AVLBalancer

class AVLTree<T : Comparable<T>> : SelfBalancingBST<T, AVLNode<T>>() {
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
