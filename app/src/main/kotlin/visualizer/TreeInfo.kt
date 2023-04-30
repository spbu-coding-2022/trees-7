package visualizer

enum class TreeType {
    RB, AVL, Simple
}

data class TreeInfo(
    val name: String,
    val type: TreeType
)
