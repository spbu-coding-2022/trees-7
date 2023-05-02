package visualizer

enum class TreeType(val displayName: String) {
    RB(displayName = "Red-Black"),
    AVL(displayName = "AVL"),
    Simple(displayName = "Simple")
}

data class TreeInfo(
    val name: String,
    val type: TreeType
)
