package visualizer.menu.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import visualizer.DBType
import visualizer.LoadingView
import visualizer.commonui.AppButton
import visualizer.commonui.defaultHeight
import visualizer.commonui.defaultTextStyle


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onGoBack: () -> Unit
) {
    val cScope = rememberCoroutineScope { Dispatchers.Default }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            viewModel.loadConfig()
        }
    }

    when (val state = viewModel.state) {
        SettingsState.Loading -> LoadingView()

        is SettingsState.Loaded -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                var selectedDBType by remember { mutableStateOf(state.conf.dbType) }
                DBTypeSelector(
                    modifier = Modifier.height(defaultHeight),
                    selectedDBType = selectedDBType,
                    onDBTypeSelect = { selectedDBType = it }
                )

                when (selectedDBType) {
                    DBType.Json -> ButtonArray(
                        modifier = Modifier.height(defaultHeight),
                        onApply = {
                            cScope.launch {
                                viewModel.applyConfig(
                                    state.conf.copy(
                                        dbType = DBType.Json
                                    )
                                )
                            }
                        },
                        onGoBack = onGoBack
                    )

                    DBType.Postgres -> PostgresFields(
                        postgresConf = state.conf.postgresConfig,
                        onApply = { newConf ->
                            cScope.launch {
                                viewModel.applyConfig(
                                    state.conf.copy(
                                        dbType = DBType.Postgres,
                                        postgresConfig = newConf
                                    )
                                )
                            }
                        },
                        onGoBack = onGoBack
                    )


                    DBType.Neo4j -> Neo4jFields(
                        neo4jConf = state.conf.neo4jConfig,
                        onApply = { newConf ->
                            cScope.launch {
                                viewModel.applyConfig(
                                    state.conf.copy(
                                        dbType = DBType.Neo4j,
                                        neo4jConfig = newConf
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
}


@Composable
private fun DBTypeSelector(
    modifier: Modifier = Modifier,
    selectedDBType: DBType,
    onDBTypeSelect: (DBType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DBType.values().forEach {
            AppButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                onClick = { onDBTypeSelect(it) }
            ) {
                Text(
                    text = it.displayName,
                    style = defaultTextStyle,
                    color = if (selectedDBType == it) MaterialTheme.colorScheme.primary else Color.Black
                )
            }
        }
    }
}
