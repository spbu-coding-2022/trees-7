package visualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import java.awt.Dimension


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
            Text("hello world")


        }
    }
}
