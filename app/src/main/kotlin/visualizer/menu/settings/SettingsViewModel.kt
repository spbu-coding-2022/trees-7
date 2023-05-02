package visualizer.menu.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import visualizer.APPDATA_PATH
import visualizer.AppConfig
import java.io.File

sealed class SettingsState {
    object Loading : SettingsState()
    data class Loaded(val conf: AppConfig) : SettingsState()
}


class SettingsViewModel {
    var state: SettingsState by mutableStateOf(SettingsState.Loading)
        private set

    /** Load config from drive */
    fun loadConfig() {
        state = SettingsState.Loading

        File(APPDATA_PATH, "config.json").run {
            state = SettingsState.Loaded(
                conf = Json.decodeFromString<AppConfig>(readText())
            )
        }
    }

    /** Save new config to drive */
    fun applyConfig(conf: AppConfig) {
        state = SettingsState.Loading

        File(APPDATA_PATH, "config.json").run {
            writeText(Json.encodeToString(conf))
        }

        state = SettingsState.Loaded(conf)
    }
}
