plugins {
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.application)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.application.compose)
    alias(libs.plugins.jp.speakbuddy.edisonandroidexercise.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    api(projects.core.designsystem)

    api(projects.feature.fact)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
}

