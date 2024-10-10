import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "jp.speakbuddy.edisonandroidexercise.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidTest") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("hilt") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidRoom") {
            version = "1.0.0"
            id = "jp.speakbuddy.edisonandroidexercise.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}