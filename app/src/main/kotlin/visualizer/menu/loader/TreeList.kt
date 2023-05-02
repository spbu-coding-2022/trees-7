package visualizer.menu.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import visualizer.TreeInfo
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHPadding
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
        }, key = { it }) { treeInfo ->

            var deleteTriggered by remember { mutableStateOf(false) }

            if (!deleteTriggered) {
                TreeCard(
                    modifier = Modifier.height(defaultHeight),
                    tree = treeInfo,
                    onEdit = { onEditTree(treeInfo) },
                    onDeleteRequest = { deleteTriggered = true }
                )
            } else {
                DeletionRequest(
                    modifier = Modifier.height(defaultHeight),
                    treeName = treeInfo.name,
                    onConfirm = {onDeleteTree(treeInfo)},
                    onCancel = { deleteTriggered = false }
                )
            }
        }
    }
}

@Composable
private fun TreeCard(
    modifier: Modifier = Modifier,
    tree: TreeInfo,
    onEdit: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AppButton(
            modifier = Modifier.fillMaxHeight().weight(1f),
            onClick = onEdit
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

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = onDeleteRequest,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete tree button",
                tint = Color.Gray,
            )
        }
    }
}

@Composable
private fun DeletionRequest(
    modifier: Modifier = Modifier,
    treeName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = defaultHPadding)
        ) {
            Text(
                text = "Are you sure you want to delete tree '$treeName'?"
                    .replace(" ", "\u00A0"), // so text overflow works property
                style = defaultTextStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = onConfirm,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Done,
                contentDescription = "Confirm deletion",
                tint = MaterialTheme.colorScheme.primary,
            )
        }

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = onCancel,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Cancel deletion",
                tint = Color.Gray,
            )
        }
    }
}
