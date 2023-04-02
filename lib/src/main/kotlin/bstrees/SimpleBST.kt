package bstrees

import bstrees.nodes.SimpleNode
import bstrees.wrapped.WrappedSimpleNode

class SimpleBST<T : Comparable<T>> : BinarySearchTree<T, SimpleNode<T>, WrappedSimpleNode<T>>() {
    override val root get() = treeRoot?.let { WrappedSimpleNode(it) }

    override fun createNewNode(data: T) = SimpleNode(data)

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
