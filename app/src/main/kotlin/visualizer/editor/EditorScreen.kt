package visualizer.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bstrees.nodes.TreeNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import visualizer.LoadingView
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.editor.graph.TreeGraph


@Composable
fun <N : TreeNode<NodeData, N>> EditorScreen(
    viewModel: EditorViewModel<N>,
    treeInfo: TreeInfo,
    onGoHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            viewModel.initTree()
        }
    }

    val cScope = rememberCoroutineScope { Dispatchers.Default }
    when (val state = viewModel.state) {
        EditorState.Loading -> LoadingView()

        is EditorState.Loaded -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(modifier = Modifier.width(370.dp)) {
                    Header(
                        treeInfo = treeInfo,
                        onSave = { cScope.launch { viewModel.saveTree(treeInfo.name) } },
                        onResetTree = { cScope.launch { viewModel.resetTree() } },
                        onGoHome = onGoHome
                    )
                    Spacer(Modifier.height(40.dp))
                    TreeControls(
                        onInsert = { key, value -> cScope.launch { viewModel.insert(key, value) } },
                        onDelete = { cScope.launch { viewModel.delete(it) } },
                        onSearch = { cScope.launch { viewModel.search(it) } }
                    )
                    Spacer(Modifier.weight(1f))
                    StatusBar(status = state.status)
                }

                Surface(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    state.treeRoot?.let {
                        TreeGraph(it, defaultNodeSize, viewModel::dragNode)
                    }
                }
            }
        }
    }
}
