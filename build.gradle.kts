buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.2.4")
    }
}

apply(plugin = "binary-compatibility-validator")

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    signing
}

version = "2.0.4"

repositories {
    jcenter()
}

dependencies {
    compileOnly(kotlin("gradle-plugin"))
    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
    testImplementation("io.kotest:kotest-assertions-core:4.3.0")
    testImplementation("io.kotest:kotest-property:4.3.0")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation(kotlin("gradle-plugin"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
}

gradlePlugin {
    val plug by plugins.creating {
        id = "com.nanit.multiplatform-swiftpackage"
        implementationClass = "com.chromaticnoise.multiplatformswiftpackage.MultiplatformSwiftPackagePlugin"
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
