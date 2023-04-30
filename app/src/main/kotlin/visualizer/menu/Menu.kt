package visualizer.menu

import androidx.compose.runtime.*
import bstrees.BinarySearchTree
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.menu.creator.CreatorScreen
import visualizer.menu.loader.LoaderScreen
import visualizer.menu.loader.LoaderViewModel
import visualizer.menu.settings.SettingsScreen

private sealed class Screen {
    object Loader : Screen()
    object Creator : Screen()
    object Settings : Screen()
}

@Composable
fun Menu(
    onEditTree: (TreeInfo, BinarySearchTree<NodeData, *>) -> Unit
) {
    var screenState by remember { mutableStateOf<Screen>(Screen.Loader) }
    when (screenState) {
        Screen.Loader -> LoaderScreen(
            viewModel = LoaderViewModel(
                onEditTree = onEditTree
            ),
            onNewTree = { screenState = Screen.Creator },
            onSettings = { screenState = Screen.Settings }
        )

        Screen.Creator -> CreatorScreen(
            onGoBack = { screenState = Screen.Loader }
        )

        Screen.Settings -> SettingsScreen()
    }
}
