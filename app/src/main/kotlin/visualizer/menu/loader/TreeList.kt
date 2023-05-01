package visualizer.menu.loader

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import visualizer.TreeInfo
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun TreeList(
    modifier: Modifier = Modifier,
    trees: List<TreeInfo>,
    searchedText: String,
    onEditTree: (TreeInfo) -> Unit,
    onDeleteTree: (TreeInfo) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(trees.filter {
            it.name.contains(searchedText, ignoreCase = true)
        }) { tree ->
            Row(
                modifier = Modifier.height(defaultHeight).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TreeCard(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    tree = tree,
                    onClick = { onEditTree(tree) }
                )
                DeleteTreeButton(
                    modifier = Modifier.fillMaxHeight().aspectRatio(1f),
                    onClick = { onDeleteTree(tree) },
                )
            }
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
            modifier = Modifier.padding(start = 20.dp), // for long tree names
            text = tree.type.displayName,
            style = defaultTextStyle
        )
    }
}

@Composable
private fun DeleteTreeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AppButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete tree button",
            tint = Color.Gray,
        )
    }
}
