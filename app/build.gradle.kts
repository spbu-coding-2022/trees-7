plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    implementation(libs.koin.core)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.exposed.core)
    implementation(libs.postgresql)
    implementation(libs.neo4j.ogm.core)

    implementation(project(":lib"))
}

compose.desktop {
    application {
        mainClass = "visualizer.MainKt"
    }
}
