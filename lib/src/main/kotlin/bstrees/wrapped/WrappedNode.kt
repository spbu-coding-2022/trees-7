package bstrees.wrapped

interface WrappedNode<T : Comparable<T>, WrappedType : WrappedNode<T, WrappedType>> {
    val data: T
    val left: WrappedType?
    val right: WrappedType?
}
