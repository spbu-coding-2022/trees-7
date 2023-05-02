plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.noarg)
    `java-library`
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.neo4j.ogm.core)
    implementation(libs.neo4j.ogm.bolt)

    testImplementation(libs.junit.jupiter)
}

noArg {
    annotation("org.neo4j.ogm.annotation.NodeEntity")
    annotation("org.neo4j.ogm.annotation.RelationshipEntity")
}

tasks.test {
    useJUnitPlatform()
}
