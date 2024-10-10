plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.feature)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library.compose)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.feature.fact"

    defaultConfig {
        testInstrumentationRunner =
            "jp.speakbuddy.edisonandroidexercise.core.common.testing.SBTestRunner"
    }
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.uiTesting)

    api(projects.feature.favoritefact)
    api(projects.feature.randomfact)

    implementation(libs.androidx.compose.material3)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}