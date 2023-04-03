package bstrees.wrapped

import bstrees.nodes.SimpleNode

class WrappedSimpleNode<T : Comparable<T>>(private val nodeToWrap: SimpleNode<T>) : WrappedNode<T, WrappedSimpleNode<T>> {
    override val data get() = nodeToWrap.data
    override val parent get() = nodeToWrap.parent?.let { WrappedSimpleNode(it) }
    override val left get() = nodeToWrap.left?.let { WrappedSimpleNode(it) }
    override val right get() = nodeToWrap.right?.let { WrappedSimpleNode(it) }
}
