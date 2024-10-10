plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.domain)
    api(projects.core.model)
    api(projects.core.network)

    implementation(libs.androidx.paging.common.android)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
}
