rootProject.name = "trees"
include("lib")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.8.20")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("kotlin-noarg", "org.jetbrains.kotlin.plugin.noarg").versionRef("kotlin")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

            version("exposed", "0.41.1")
            library("exposed-core", "org.jetbrains.exposed", "exposed-core").versionRef("exposed")
            library("exposed-dao", "org.jetbrains.exposed", "exposed-dao").versionRef("exposed")
            library("exposed-jdbc", "org.jetbrains.exposed", "exposed-jdbc").versionRef("exposed")

            version("neo4j-ogm", "4.0.5")
            library("neo4j-ogm-core", "org.neo4j", "neo4j-ogm-core").versionRef("neo4j-ogm")
            library("neo4j-ogm-bolt", "org.neo4j", "neo4j-ogm-bolt-driver").versionRef("neo4j-ogm")

            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:5.9.2")
        }
    }
}
