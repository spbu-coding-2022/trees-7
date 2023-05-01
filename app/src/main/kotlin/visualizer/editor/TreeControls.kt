package visualizer.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.commonui.AppButton
import visualizer.commonui.AppTextField
import visualizer.commonui.defaultHeight


@Composable
fun TreeControls(
    modifier: Modifier = Modifier,
    onInsert: (Int, String) -> Unit,
    onDelete: (Int) -> Unit,
    onSearch: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        InsertBar(onInsert = onInsert)
        DeleteBar(onDelete = onDelete)
        SearchBar(onSearch = onSearch)
    }
}

@Composable
private fun InsertBar(
    modifier: Modifier = Modifier,
    onInsert: (Int, String) -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var keyText by remember { mutableStateOf("") }
        AppTextField(
            modifier = Modifier.fillMaxHeight().weight(1f),
            value = keyText,
            onValueChange = {
                if (it.isEmpty() || it == "-" || it.toIntOrNull() != null) {
                    keyText = it
                }
            },
            placeholderText = "Key"
        )

        var valueText by remember { mutableStateOf("") }
        AppTextField(
            modifier = Modifier.fillMaxHeight().weight(1.5f),
            value = valueText,
            onValueChange = { if (it.length <= 25) valueText = it },
            placeholderText = "Value to insert"
        )

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = {
                onInsert(keyText.toInt(), valueText)
                keyText = ""
                valueText = ""
            },
            enabled = keyText.toIntOrNull() != null && valueText.isNotEmpty(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Insert button",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun DeleteBar(
    modifier: Modifier = Modifier,
    onDelete: (Int) -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var keyText by remember { mutableStateOf("") }
        AppTextField(
            modifier = Modifier.fillMaxHeight().weight(1f),
            value = keyText,
            onValueChange = {
                if (it.isEmpty() || it == "-" || it.toIntOrNull() != null) {
                    keyText = it
                }
            },
            placeholderText = "Key to delete"
        )

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = {
                onDelete(keyText.toInt())
                keyText = ""
            },
            enabled = keyText.toIntOrNull() != null,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete button",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (Int) -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        var keyText by remember { mutableStateOf("") }
        AppTextField(
            modifier = Modifier.fillMaxHeight().weight(1f),
            value = keyText,
            onValueChange = {
                if (it.isEmpty() || it == "-" || it.toIntOrNull() != null) {
                    keyText = it
                }
            },
            placeholderText = "Key to search for"
        )

        AppButton(
            modifier = Modifier.fillMaxHeight().aspectRatio(1f),
            onClick = {
                onSearch(keyText.toInt())
                keyText = ""
            },
            enabled = keyText.toIntOrNull() != null,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search button",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

