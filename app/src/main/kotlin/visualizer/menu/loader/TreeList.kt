package visualizer.menu.loader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import visualizer.TreeInfo
import visualizer.TreeType
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun TreeList(
    modifier: Modifier = Modifier,
    trees: List<TreeInfo>,
    searchedText: String,
    onEditTree: (TreeInfo) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(trees.filter {
            it.name.contains(searchedText, ignoreCase = true)
        }) { tree ->
            TreeCard(
                modifier = Modifier.fillMaxWidth().height(defaultHeight),
                tree = tree,
                onClick = { onEditTree(tree) }
            )
        }
    }
}

@Composable
private fun TreeCard(
    modifier: Modifier = Modifier,
    tree: TreeInfo,
    onClick: () -> Unit
) {
    AppButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = tree.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = defaultTextStyle
        )
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = when (tree.type) {
                TreeType.RB -> "Red-Black"
                TreeType.AVL -> "AVL"
                TreeType.Simple -> "Simple"
            },
            style = defaultTextStyle
        )
    }
}
