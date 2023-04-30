package visualizer.menu.loader

enum class TreeType {
    RB, AVL, Simple
}

data class TreeCardData(
    val name: String,
    val type: TreeType
)
