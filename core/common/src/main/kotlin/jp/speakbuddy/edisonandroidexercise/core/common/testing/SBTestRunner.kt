package jp.speakbuddy.edisonandroidexercise.core.common.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom test runner for SpeakBuddy Android tests.
 * Extends AndroidJUnitRunner to provide Hilt support in instrumented tests.
 * Overrides newApplication to use HiltTestApplication for dependency injection.
 */
class SBTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, name: String, context: Context): Application =
        super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
