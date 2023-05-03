package visualizer

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

val APPDATA_PATH = "${System.getProperty("user.home")}/.bst_visualizer"

/** Creates app config if not exists */
fun initConfig() {
    File(APPDATA_PATH).mkdirs()
    File(APPDATA_PATH, "config.json").run {
        if (createNewFile()) { // if file doesn't exist
            writeText(
                Json.encodeToString(
                    AppConfig(
                        dbType = DBType.Json,
                        relativeJsonDirPath = "saved_trees",
                        postgresConfig = PostgresConfig(),
                        neo4jConfig = Neo4jConfig()
                    )
                )
            )
        }
    }
}

@Serializable
data class AppConfig(
    val dbType: DBType,

    val relativeJsonDirPath: String,
    val postgresConfig: PostgresConfig,
    val neo4jConfig: Neo4jConfig,
)

enum class DBType(val displayName: String) {
    Json("Local files"),
    Postgres("Postgres"),
    Neo4j("Neo4j")
}

@Serializable
data class PostgresConfig(
    val uri: String = "",
    val dbName: String = "",
    val user: String = "",
    val password: String = ""
)

@Serializable
data class Neo4jConfig(
    val uri: String = "",
    val user: String = "",
    val password: String = ""
)

