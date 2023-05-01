package visualizer.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import visualizer.commonui.defaultErrorColor
import visualizer.commonui.defaultHPadding
import visualizer.commonui.defaultTextStyle


enum class StatusType {
    Ok,
    Fail
}

@Composable
fun StatusBar(
    modifier: Modifier = Modifier,
    statusText: String,
    statusType: StatusType
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = defaultHPadding, vertical = 20.dp)
    ) {
        Text(
            text = statusText,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = defaultTextStyle,
            color = when (statusType) {
                StatusType.Ok -> Color.Black
                StatusType.Fail -> defaultErrorColor
            }
        )
    }
}
