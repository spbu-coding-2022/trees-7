package bstrees.balancers

import bstrees.nodes.RBNode

class RBBalancer<T : Comparable<T>> : TreeBalancer<T, RBNode<T>> {
    override fun balanceAfterInsertion(node: RBNode<T>): RBNode<T> {
        TODO("Not yet implemented")
    }

    override fun balanceAfterDeletion(node: RBNode<T>): RBNode<T> {
        TODO("Not yet implemented")
    }
}
