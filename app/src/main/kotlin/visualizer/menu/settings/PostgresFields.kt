package visualizer.menu.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import visualizer.PostgresConfig
import visualizer.commonui.AppTextField
import visualizer.commonui.defaultHeight


@Composable
fun PostgresFields(
    postgresConf: PostgresConfig,
    onApply: (PostgresConfig) -> Unit,
    onGoBack: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        var uri by remember { mutableStateOf(postgresConf.uri) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter URI. E.g. 'localhost:5432'",
            value = uri,
            onValueChange = { if (it.length <= 50) uri = it }
        )

        var dbName by remember { mutableStateOf(postgresConf.dbName) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter database name",
            value = dbName,
            onValueChange = { if (it.length <= 50) dbName = it }
        )

        var username by remember { mutableStateOf(postgresConf.user) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter user name",
            value = username,
            onValueChange = { if (it.length <= 50) username = it }
        )

        var password by remember { mutableStateOf(postgresConf.password) }
        AppTextField(
            modifier = Modifier.fillMaxWidth().height(defaultHeight),
            placeholderText = "Please enter password",
            value = password,
            onValueChange = { if (it.length <= 50) password = it }
        )

        ButtonArray(
            modifier = Modifier.height(defaultHeight),
            applyButtonEnabled = listOf(uri, dbName, username, password).all(String::isNotEmpty),
            onApply = {
                onApply(
                    PostgresConfig(
                        uri = uri,
                        dbName = dbName,
                        user = username,
                        password = password
                    )
                )
            },
            onGoBack = onGoBack
        )
    }
}
