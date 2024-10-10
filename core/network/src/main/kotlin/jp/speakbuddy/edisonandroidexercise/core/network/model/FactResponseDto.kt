package jp.speakbuddy.edisonandroidexercise.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class FactResponseDto(
    val fact: String,
    val length: Int
)
