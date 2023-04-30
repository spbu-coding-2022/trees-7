package visualizer.commonui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/** Horizontal padding */
val defaultHPadding = 25.dp

/** Vertical padding */
val defaultVPadding = 10.dp

/** Default height for buttons and text fields */
val defaultHeight = 65.dp

val defaultTextStyle: TextStyle
    @Composable
    get() = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)

val defaultBackgroundColor = Color(245, 245, 247)
