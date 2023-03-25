package bstrees.balancers

import bstrees.nodes.AVLNode

class AVLBalancer<T : Comparable<T>> : TreeBalancer<T, AVLNode<T>> {
    override fun balanceAfterInsertion(node: AVLNode<T>): AVLNode<T> {
        TODO("Not yet implemented")
    }

    override fun balanceAfterDeletion(node: AVLNode<T>): AVLNode<T> {
        TODO("Not yet implemented")
    }
}
