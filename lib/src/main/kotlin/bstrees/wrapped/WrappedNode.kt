package bstrees.wrapped

interface WrappedNode<T : Comparable<T>, WrappedNodeType : WrappedNode<T, WrappedNodeType>> {
    val data: T
    val parent: WrappedNodeType?
    val left: WrappedNodeType?
    val right: WrappedNodeType?
}
