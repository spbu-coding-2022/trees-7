package visualizer.menu.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.Neo4jConfig
import visualizer.commonui.AppTextField
import visualizer.commonui.defaultHeight


@Composable
fun Neo4jFields(
    neo4jConf: Neo4jConfig,
    onApply: (Neo4jConfig) -> Unit,
    onGoBack: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        var uri by remember { mutableStateOf(neo4jConf.uri) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter URI. E.g. 'localhost:7687'",
            value = uri,
            onValueChange = { if (it.length <= 50) uri = it }
        )

        var username by remember { mutableStateOf(neo4jConf.user) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter user name",
            value = username,
            onValueChange = { if (it.length <= 50) username = it }
        )

        var password by remember { mutableStateOf(neo4jConf.password) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter password",
            value = password,
            onValueChange = { if (it.length <= 50) password = it }
        )

        ButtonArray(
            modifier = Modifier.height(defaultHeight),
            applyButtonEnabled = listOf(uri, username, password).all(String::isNotEmpty),
            onApply = {
                onApply(
                    Neo4jConfig(
                        uri = uri,
                        user = username,
                        password = password
                    )
                )
            },
            onGoBack = onGoBack
        )
    }
}
