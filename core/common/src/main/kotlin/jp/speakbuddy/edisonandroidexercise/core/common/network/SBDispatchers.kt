package jp.speakbuddy.edisonandroidexercise.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val sbDispatcher: SBDispatchers)

enum class SBDispatchers {
    Default,
    IO,
}