package visualizer.menu.creator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import visualizer.ErrorView
import visualizer.LoadingView
import visualizer.TreeInfo
import visualizer.TreeType
import visualizer.commonui.AppButton
import visualizer.commonui.AppTextField
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


/** Allows user to create new trees */
@Composable
fun CreatorScreen(
    viewModel: CreatorViewModel,
    onGoBack: () -> Unit
) {
    val cScope = rememberCoroutineScope { Dispatchers.Default }
    when (val state = viewModel.state) {
        CreatorState.Loading -> LoadingView()

        is CreatorState.Error -> ErrorView(
            errorMsg = state.msg,
            buttonText = "Retry",
            onClick = viewModel::tryAgain
        )

        CreatorState.Ok -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                var selectedTreeType: TreeType? by remember { mutableStateOf(null) }
                TreeTypeSelector(
                    selectedTreeType = selectedTreeType,
                    onTreeTypeSelect = { selectedTreeType = it }
                )

                var treeName by remember { mutableStateOf("") }
                AppTextField(
                    modifier = Modifier.fillMaxWidth().height(defaultHeight),
                    value = treeName,
                    onValueChange = { if (it.length <= 100) treeName = it },
                    placeholderText = "Enter tree name to create"
                )

                ButtonArray(
                    createButtonEnabled = selectedTreeType != null && treeName.isNotEmpty(),
                    onCreate = {
                        cScope.launch {
                            viewModel.createTree(
                                TreeInfo(
                                    treeName,
                                    selectedTreeType ?: throw IllegalStateException()
                                )
                            )
                        }
                    },
                    onGoBack = onGoBack
                )
            }
        }
    }
}


@Composable
private fun ButtonArray(
    modifier: Modifier = Modifier,
    createButtonEnabled: Boolean,
    onCreate: () -> Unit,
    onGoBack: () -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AppButton(
            modifier = Modifier.fillMaxHeight(),
            onClick = onCreate,
            enabled = createButtonEnabled
        ) {
            Text(
                text = "Create",
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


@Composable
private fun TreeTypeSelector(
    modifier: Modifier = Modifier,
    selectedTreeType: TreeType?,
    onTreeTypeSelect: (TreeType) -> Unit
) {
    Row(
        modifier = modifier.height(defaultHeight),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TreeType.values().forEach {
            AppButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                onClick = { onTreeTypeSelect(it) }
            ) {
                Text(
                    text = it.displayName,
                    style = defaultTextStyle,
                    color = if (selectedTreeType == it) MaterialTheme.colorScheme.primary else Color.Black
                )
            }
        }
    }
}
