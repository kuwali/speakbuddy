plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.feature)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library.compose)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.feature.randomfact"

    defaultConfig {
        testInstrumentationRunner =
            "jp.speakbuddy.edisonandroidexercise.core.common.testing.SBTestRunner"
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.data)
    api(projects.core.designsystem)
    api(projects.core.domain)
    api(projects.core.model)
    api(projects.core.uiTesting)

    implementation(libs.androidx.compose.material3)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}