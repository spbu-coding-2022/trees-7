package visualizer.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.TreeInfo
import visualizer.TreeType
import visualizer.editor.graph.TreeGraph


@Composable
fun EditorScreen() {
    val viewModel = remember { EditorViewModel() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(modifier = Modifier.width(370.dp)) {
            Header(
                tree = TreeInfo("Best tree in the world", TreeType.AVL),
                onSave = { viewModel.changeTree() },
                onResetTree = {},
                onGoHome = {}
            )
            Spacer(Modifier.height(40.dp))
            TreeControls(
                onInsert = { _, _ -> },
                onDelete = {},
                onSearch = {}
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize().weight(1f),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            viewModel.drawableRoot?.let {
                TreeGraph(it, defaultNodeSize, viewModel::dragNode)
            }
        }
    }
}
