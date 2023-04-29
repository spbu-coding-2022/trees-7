package visualizer.menu.loader

enum class TreeType {
    RB, AVL, Simple
}

data class Tree(
    val name: String,
    val type: TreeType
)
