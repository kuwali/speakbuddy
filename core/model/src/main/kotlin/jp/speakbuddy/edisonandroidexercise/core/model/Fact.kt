package jp.speakbuddy.edisonandroidexercise.core.model

data class Fact(
    val fact: String,
    val length: Int,
    val isFavorite: Boolean = false,
)