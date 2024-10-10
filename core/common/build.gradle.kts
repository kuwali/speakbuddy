plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.hilt.android.testing)
}
