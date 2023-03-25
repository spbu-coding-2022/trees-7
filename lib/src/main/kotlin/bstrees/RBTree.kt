package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer

class RBTree<T : Comparable<T>> : SelfBalancingBST<T, RBNode<T>>() {
    override fun createNewNode(data: T) = RBNode(data)
    override val balancer = RBBalancer<T>()

    override fun insert(data: T) {
        TODO("Not yet implemented")
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
