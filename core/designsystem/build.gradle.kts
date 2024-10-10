plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library.compose)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.compose.material3)
}