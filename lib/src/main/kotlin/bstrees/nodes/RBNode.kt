package bstrees.nodes

class RBNode<T : Comparable<T>>(override var data: T) : TreeNode<T, RBNode<T>> {
    enum class Color {
        Red,
        Black
    }

    override var parent: RBNode<T>? = null
    override var left: RBNode<T>? = null
    override var right: RBNode<T>? = null
    var color = Color.Black
}
