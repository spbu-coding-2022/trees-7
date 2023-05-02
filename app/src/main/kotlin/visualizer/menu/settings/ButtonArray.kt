package visualizer.menu.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.commonui.AppButton
import visualizer.commonui.defaultTextStyle

@Composable
fun ButtonArray(
    modifier: Modifier = Modifier,
    applyButtonEnabled: Boolean = true,
    onApply: () -> Unit,
    onGoBack: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AppButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = onApply,
            enabled = applyButtonEnabled
        ) {
            Text(
                text = "Apply",
                style = defaultTextStyle,
                color = MaterialTheme.colorScheme.primary
            )
        }

        AppButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = onGoBack,
        ) {
            Text(
                text = "Go back",
                style = defaultTextStyle,
            )
        }
    }
}
