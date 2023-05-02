package visualizer

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Allows to store key, value & x, y coordinates in BSTs nodes */
class NodeData(
    val key: Int,
    val value: String,
    var x: Dp = 0.dp,
    var y: Dp = 0.dp
) : Comparable<NodeData> {
    override fun compareTo(other: NodeData) = key.compareTo(other.key)

    override fun toString(): String =
        "key=$key value=$value\nx=$x y=$y"

    companion object {
        fun serialize(data: NodeData): String =
            "${data.x.value};${data.y.value};${data.key};${data.value}"

        fun deserialize(data: String): NodeData =
            data.split(";", limit = 4).let {
                NodeData(
                    key = it[2].toInt(),
                    value = it[3],
                    x = it[0].toFloat().dp,
                    y = it[1].toFloat().dp
                )
            }
    }
}
