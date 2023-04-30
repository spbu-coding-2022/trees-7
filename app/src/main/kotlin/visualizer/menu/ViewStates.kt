package visualizer.menu

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


@Composable
fun LoadingView() {
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Loading..",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = defaultTextStyle
        )
    }
}


@Composable
fun ErrorView(
    errorMsg: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
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