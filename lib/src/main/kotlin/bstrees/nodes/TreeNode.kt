package bstrees.nodes

abstract class TreeNode<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    abstract var data: T
        internal set
    internal var parent: NodeType? = null
    var left: NodeType? = null
        internal set
    var right: NodeType? = null
        internal set
}
