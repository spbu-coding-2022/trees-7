package visualizer.menu.loader

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun LoaderScreen(
    onNewTree: () -> Unit,
    onSettings: () -> Unit
) {
    val viewModel = remember { LoaderViewModel() }

    Column {
        var searchText by remember { mutableStateOf("") }
        Header(
            modifier = Modifier.height(defaultHeight),
            searchTextProvider = { searchText },
            onSearchTextChange = { searchText = it },
            onNewTree = onNewTree,
            onSettings = onSettings
        )

        if (viewModel.trees.isEmpty()) {
            NoTreesFound()
        } else {
            Spacer(Modifier.height(40.dp))
            TreeList(trees = viewModel.trees, searchedText = searchText)
        }
    }
}

@Composable
fun NoTreesFound() {
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "No trees found.\nYou create a new one by clicking 'New tree' button",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = defaultTextStyle
        )
    }
}
