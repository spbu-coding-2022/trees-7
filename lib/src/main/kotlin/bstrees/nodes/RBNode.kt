package bstrees.nodes

class RBNode<E : Comparable<E>>(data: E) : TreeNode<E, RBNode<E>>(data) {
    enum class Color {
        Red,
        Black
    }

    var color = Color.Black
        internal set

    internal fun flipColor() {
        color = if (color == Color.Black) Color.Red else Color.Black
    }
}
