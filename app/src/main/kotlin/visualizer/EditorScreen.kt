package visualizer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.graph.TreeGraph
import visualizer.viewmodels.EditorViewModel


@Composable
fun EditorScreen() {
    val viewModel = remember { EditorViewModel() }

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Surface(
            modifier = Modifier.fillMaxSize().weight(1f),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            viewModel.drawableRoot?.let {
                TreeGraph(it, viewModel.nodeSize, viewModel::dragNode)
            }
        }

        Button(
            onClick = viewModel::changeTree
        ) {
            Text("Update")
        }
    }
}
