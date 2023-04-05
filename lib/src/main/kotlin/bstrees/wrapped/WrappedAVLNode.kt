package bstrees.wrapped

import bstrees.nodes.AVLNode

class WrappedAVLNode<T : Comparable<T>>(private val nodeToWrap: AVLNode<T>) : WrappedNode<T, WrappedAVLNode<T>> {
    override val data get() = nodeToWrap.data
    override val left get() = nodeToWrap.left?.let { WrappedAVLNode(it) }
    override val right get() = nodeToWrap.right?.let { WrappedAVLNode(it) }
    val height get() = nodeToWrap.height
}
