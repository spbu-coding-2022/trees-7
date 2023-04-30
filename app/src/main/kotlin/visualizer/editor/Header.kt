package visualizer.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import visualizer.TreeInfo
import visualizer.TreeType
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHPadding
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun Header(
    modifier: Modifier = Modifier,
    treeInfo: TreeInfo,
    onSave: () -> Unit,
    onResetTree: () -> Unit,
    onGoHome: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TreeInfoBar(tree = treeInfo)
        EditorControls(
            onSave = onSave,
            onResetTree = onResetTree,
            onGoHome = onGoHome
        )
    }
}

@Composable
private fun TreeInfoBar(modifier: Modifier = Modifier, tree: TreeInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(defaultHeight)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = defaultHPadding)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = tree.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = defaultTextStyle,
        )
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = when (tree.type) {
                TreeType.Simple -> "simple"
                TreeType.RB -> "rb"
                TreeType.AVL -> "avl"
            },
            style = defaultTextStyle,
            color = Color.LightGray
        )
    }
}

@Composable
private fun EditorControls(
    modifier: Modifier = Modifier,
    onSave: () -> Unit,
    onResetTree: () -> Unit,
    onGoHome: () -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AppButton(
            modifier = Modifier.fillMaxHeight().weight(1f),
            onClick = onSave
        ) {
            Text(
                text = "Save",
                color = MaterialTheme.colorScheme.primary,
                style = defaultTextStyle,
            )
        }

        AppButton(
            modifier = Modifier.fillMaxHeight().weight(2f),
            onClick = onResetTree
        ) {
            Text(
                text = "Reset tree",
                style = defaultTextStyle,
            )
        }

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = onGoHome,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Go Home button",
                tint = Color.LightGray
            )
        }
    }
}
