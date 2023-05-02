package visualizer.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import bstrees.nodes.TreeNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.editor.graph.DrawableNode
import visualizer.editor.graph.TreeGraph


@Composable
fun <N : TreeNode<NodeData, N>> EditorScreen(
    viewModel: EditorViewModel<N>,
    treeInfo: TreeInfo,
    onGoHome: () -> Unit
) {
    val cScope = rememberCoroutineScope { Dispatchers.Default }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            viewModel.initTree()
        }
    }

    var isLoading = remember { false }
    var treeRoot: DrawableNode? by remember { mutableStateOf(null) }
    var status = remember { EditorStatus("", StatusType.Ok) }

    when (val state = viewModel.state) {
        EditorState.Loading -> {
            status = EditorStatus("Loading..", StatusType.Ok)
            isLoading = true
        }

        EditorState.Saving -> {
            status = EditorStatus("Saving..", StatusType.Ok)
            isLoading = true
        }

        is EditorState.Loaded -> {
            treeRoot = state.treeRoot
            status = state.status
            isLoading = false
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(modifier = Modifier.width(370.dp)) {
            Header(
                treeInfo = treeInfo,
                onSave = { cScope.launch { viewModel.saveTree(treeInfo.name) } },
                onResetTree = { cScope.launch { viewModel.resetTree() } },
                onGoHome = onGoHome,
                enabled = !isLoading
            )
            Spacer(Modifier.height(40.dp))
            TreeControls(
                onInsert = { key, value -> cScope.launch { viewModel.insert(key, value) } },
                onDelete = { cScope.launch { viewModel.delete(it) } },
                onSearch = { cScope.launch { viewModel.search(it) } },
                enabled = !isLoading
            )
            Spacer(Modifier.weight(1f))
            StatusBar(status = status)
        }

        Surface(
            modifier = Modifier.fillMaxSize().weight(1f),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            treeRoot?.let {
                TreeGraph(it, defaultNodeSize, viewModel::dragNode)
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {} // block mouse events in the TreeGraph zone
                )
            }
        }
    }
}
