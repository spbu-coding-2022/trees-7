package bstrees

import bstrees.nodes.AVLNode
import bstrees.balancers.AVLBalancer

class AVLTree<T : Comparable<T>> : SelfBalancingBST<T, AVLNode<T>>() {
    override fun createNewNode(data: T) = AVLNode(data)
    override val balancer = AVLBalancer<T>()

    override fun insert(data: T) {
        TODO("Not yet implemented")
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
