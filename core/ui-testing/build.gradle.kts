plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.uitesting"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}