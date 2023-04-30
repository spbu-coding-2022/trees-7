package visualizer.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bstrees.nodes.TreeNode
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.editor.graph.TreeGraph


@Composable
fun <N : TreeNode<NodeData, N>> EditorScreen(
    viewModel: EditorViewModel<N>,
    treeInfo: TreeInfo,
    onGoHome: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(modifier = Modifier.width(370.dp)) {
            Header(
                treeInfo = treeInfo,
                onSave = { viewModel.changeTree() },
                onResetTree = {},
                onGoHome = onGoHome
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
