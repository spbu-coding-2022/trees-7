package bstrees.nodes

class SimpleNode<T : Comparable<T>>(override var data: T) : TreeNode<T, SimpleNode<T>> {
    override var parent: SimpleNode<T>? = null
    override var left: SimpleNode<T>? = null
    override var right: SimpleNode<T>? = null
}
