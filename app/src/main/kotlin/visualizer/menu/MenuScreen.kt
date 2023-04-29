package visualizer.menu

import androidx.compose.runtime.*
import visualizer.menu.creator.CreatorScreen
import visualizer.menu.loader.LoaderScreen
import visualizer.menu.settings.SettingsScreen

private sealed class Screen {
    object Loader : Screen()
    object Creator : Screen()
    object Settings : Screen()
}

@Composable
fun MenuScreen() {
    var screenState by remember { mutableStateOf<Screen>(Screen.Loader) }
    when (val screen = screenState) {
        Screen.Loader -> LoaderScreen(
            onNewTree = { screenState = Screen.Creator },
            onSettings = { screenState = Screen.Settings }
        )

        Screen.Creator -> CreatorScreen()
        Screen.Settings -> SettingsScreen()
    }
}
