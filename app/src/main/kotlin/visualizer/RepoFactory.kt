package visualizer

import bstrees.repos.JsonRepository
import bstrees.repos.Neo4jRepository
import bstrees.repos.SqlRepository
import bstrees.repos.TreeRepository
import bstrees.repos.strategies.AVLStrategy
import bstrees.repos.strategies.RBStrategy
import bstrees.repos.strategies.SimpleStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.neo4j.ogm.config.Configuration
import java.io.File


object RepoFactory {
    private fun loadConfig(): AppConfig {
        File(APPDATA_PATH, "config.json").run {
            return Json.decodeFromString<AppConfig>(readText())
        }
    }

    fun getRepo(treeType: TreeType): TreeRepository<*> {
        val strategy = when (treeType) {
            TreeType.Simple -> SimpleStrategy(NodeData::serialize, NodeData::deserialize)
            TreeType.AVL -> AVLStrategy(NodeData::serialize, NodeData::deserialize)
            TreeType.RB -> RBStrategy(NodeData::serialize, NodeData::deserialize)
        }

        val conf = loadConfig()
        return when (conf.dbType) {
            DBType.Json -> {
                JsonRepository(
                    strategy,
                    "$APPDATA_PATH/${conf.relativeJsonDirPath}"
                )
            }

            DBType.Postgres -> {
                val postgresConfig = conf.postgresConfig

                SqlRepository(
                    strategy,
                    Database.connect(
                        driver = "org.postgresql.Driver",
                        url = "jdbc:postgresql://${postgresConfig.uri}/${postgresConfig.dbName}",
                        user = postgresConfig.user,
                        password = postgresConfig.password
                    )
                )
            }

            DBType.Neo4j -> {
                val neo4jConfig = conf.neo4jConfig

                Neo4jRepository(
                    strategy,
                    Configuration.Builder()
                        .uri("bolt://${neo4jConfig.uri}")
                        .credentials(neo4jConfig.user, neo4jConfig.password)
                        .build()
                )
            }
        }
    }
}
