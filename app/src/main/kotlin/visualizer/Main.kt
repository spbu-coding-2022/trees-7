package visualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import visualizer.editor.EditorScreen
import visualizer.menu.MenuScreen
import java.awt.Dimension


private sealed class Screen {
    object Menu : Screen()
    object Editor : Screen()
}

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(700.dp, 700.dp)
            ),
        ) {
            LocalDensity.current.run {
                window.minimumSize = Dimension(350.dp.roundToPx(), 350.dp.roundToPx())
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(245, 245, 247))
                    .padding(30.dp)
            ) {
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(
                        surface = Color.White,
                    )
                ) {
                    var screenState by remember { mutableStateOf<Screen>(Screen.Menu) }
                    when (val screen = screenState) {
                        Screen.Menu -> MenuScreen()
                        Screen.Editor -> EditorScreen()
                    }
                }
            }
        }
    }
}
