package bstrees.nodes

class AVLNode<T : Comparable<T>>(override var data: T) : TreeNode<T, AVLNode<T>> {
    override var parent: AVLNode<T>? = null
    override var left: AVLNode<T>? = null
    override var right: AVLNode<T>? = null
    var height = 0
}
