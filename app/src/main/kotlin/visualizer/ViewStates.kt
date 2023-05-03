package visualizer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import visualizer.commonui.AppButton
import visualizer.commonui.defaultErrorColor
import visualizer.commonui.defaultTextStyle


/** Displays loading screen */
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    loadingText: String = "Loading.."
) {
    Box(modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.BottomStart),
            text = loadingText,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = defaultTextStyle
        )
    }
}


/** Displays error screen */
@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMsg: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMsg,
                color = defaultErrorColor,
                style = defaultTextStyle
            )
            AppButton(
                onClick = onClick
            ) {
                Text(
                    text = buttonText,
                    style = defaultTextStyle
                )
            }
        }
    }
}
