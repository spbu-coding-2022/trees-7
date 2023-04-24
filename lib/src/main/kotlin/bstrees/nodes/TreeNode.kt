package bstrees.nodes

abstract class TreeNode<E : Comparable<E>, N : TreeNode<E, N>>(data: E) {
    var data: E = data
        internal set

    internal var parent: N? = null
    var left: N? = null
        internal set
    var right: N? = null
        internal set
}
