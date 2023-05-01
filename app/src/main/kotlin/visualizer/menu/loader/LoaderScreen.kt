package visualizer.menu.loader

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle
import visualizer.LoadingView


/** Displays trees loaded from database. Lets user select a tree to edit */
@Composable
fun LoaderScreen(
    viewModel: LoaderViewModel,
    onNewTree: () -> Unit,
    onSettings: () -> Unit
) {
    LaunchedEffect(Unit) { // on first composition load all trees from db
        withContext(Dispatchers.Default) {
            viewModel.loadTrees()
        }
    }

    val cScope = rememberCoroutineScope { Dispatchers.Default }
    when (val state = viewModel.state) {
        LoaderState.Loading -> LoadingView()

        is LoaderState.Loaded ->
            Column {
                var searchText by remember { mutableStateOf("") }
                Header(
                    modifier = Modifier.height(defaultHeight),
                    searchTextProvider = { searchText },
                    onSearchTextChange = { searchText = it },
                    onNewTree = onNewTree,
                    onSettings = onSettings
                )

                if (state.trees.isEmpty()) {
                    NoTreesFound()
                } else {
                    Spacer(Modifier.height(40.dp))
                    TreeList(
                        trees = state.trees,
                        searchedText = searchText,
                        onEditTree = { cScope.launch { viewModel.editTree(it) } }
                    )
                }
            }
    }
}

@Composable
private fun NoTreesFound() {
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
