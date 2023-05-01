package visualizer.menu

import androidx.compose.runtime.*
import bstrees.BinarySearchTree
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.menu.creator.CreatorScreen
import visualizer.menu.creator.CreatorViewModel
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
    onEditTree: (TreeInfo, BinarySearchTree<NodeData, *>) -> Unit // called when user wants to edit BST
) {
    var screenState by remember { mutableStateOf<Screen>(Screen.Loader) }
    when (screenState) {
        Screen.Loader -> LoaderScreen(
            viewModel = LoaderViewModel(onEditTree = onEditTree),
            onNewTree = { screenState = Screen.Creator },
            onSettings = { screenState = Screen.Settings }
        )

        Screen.Creator -> CreatorScreen(
            viewModel = CreatorViewModel(onEditTree = onEditTree),
            onGoBack = { screenState = Screen.Loader }
        )

        Screen.Settings -> SettingsScreen()
    }
}
