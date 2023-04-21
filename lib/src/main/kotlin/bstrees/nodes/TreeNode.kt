package bstrees.nodes

abstract class TreeNode<T : Comparable<T>, NodeType : TreeNode<T, NodeType>>(data: T) {
    var data: T = data
        internal set

    internal var parent: NodeType? = null
    var left: NodeType? = null
        internal set
    var right: NodeType? = null
        internal set
}
