package visualizer.menu.loader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun TreeList(modifier: Modifier = Modifier, trees: List<Tree>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(trees) {
            TreeCard(
                modifier = Modifier.fillMaxWidth().height(defaultHeight),
                tree = it
            )
        }
    }
}

@Composable
private fun TreeCard(modifier: Modifier = Modifier, tree: Tree) {
    AppButton(
        modifier = modifier,
        onClick = {}
    ) {
        Text(
            text = tree.name,
            style = defaultTextStyle
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = when (tree.type) {
                TreeType.RB -> "Red-Black"
                TreeType.AVL -> "AVL"
                TreeType.Simple -> "Simple"
            },
            style = defaultTextStyle
        )
    }
}
