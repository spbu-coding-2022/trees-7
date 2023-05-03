package visualizer.menu.loader

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import visualizer.commonui.AppButton
import visualizer.commonui.AppTextField
import visualizer.commonui.defaultTextStyle


@Composable
fun Header(
    modifier: Modifier = Modifier,
    searchTextProvider: () -> String,
    onSearchTextChange: (String) -> Unit,
    onNewTree: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxHeight().weight(1f),
            searchTextProvider = searchTextProvider,
            onSearchTextChange = onSearchTextChange
        )
        NewTreeButton(modifier = Modifier.fillMaxHeight(), onClick = onNewTree)
        SettingsButton(modifier = Modifier.fillMaxHeight(), onClick = onSettings)
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    searchTextProvider: () -> String,
    onSearchTextChange: (String) -> Unit
) {
    AppTextField(
        modifier = modifier,
        value = searchTextProvider(),
        onValueChange = onSearchTextChange,
        placeholderText = "Search for tree by name"
    )
}

@Composable
private fun NewTreeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    AppButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = "New tree",
            color = MaterialTheme.colorScheme.primary,
            style = defaultTextStyle
        )
    }
}

@Composable
private fun SettingsButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    AppButton(
        modifier = modifier.aspectRatio(1f),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = "Settings button",
            tint = Color.Gray
        )
    }
}
