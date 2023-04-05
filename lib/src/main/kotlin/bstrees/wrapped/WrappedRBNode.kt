package bstrees.wrapped

import bstrees.nodes.RBNode

class WrappedRBNode<T : Comparable<T>>(private val nodeToWrap: RBNode<T>) : WrappedNode<T, WrappedRBNode<T>> {
    override val data get() = nodeToWrap.data
    override val left get() = nodeToWrap.left?.let { WrappedRBNode(it) }
    override val right get() = nodeToWrap.right?.let { WrappedRBNode(it) }
    val color get() = nodeToWrap.color
}
