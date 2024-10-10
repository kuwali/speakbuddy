plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.library)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.core.domain"
}

dependencies {
    api(projects.core.model)

    implementation(libs.androidx.paging.common.android)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
}
