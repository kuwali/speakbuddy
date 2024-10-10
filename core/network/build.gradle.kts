plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "API_URL", "\"https://catfact.ninja/\"")
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.okhttp)
    implementation(libs.retrofit)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
}
