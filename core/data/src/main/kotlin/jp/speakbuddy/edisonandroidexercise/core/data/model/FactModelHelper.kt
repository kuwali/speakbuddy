package jp.speakbuddy.edisonandroidexercise.core.data.model

import jp.speakbuddy.edisonandroidexercise.core.database.model.FactEntity
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto

/**
 * Converts a [FactResponseDto] to a [FactEntity].
 *
 * @return A [FactEntity] with the fact and length from the [FactResponseDto].
 */
fun FactResponseDto.toEntity(): FactEntity = FactEntity(
    fact = fact,
    length = length
)

/**
 * Converts a [FactEntity] to a [Fact] model.
 *
 * @return A [Fact] with the fact, length, and favorite status from the [FactEntity].
 */
fun FactEntity.toModel(): Fact = Fact(
    fact = fact,
    length = length,
    isFavorite = isFavorite
)

/**
 * Converts a [FactResponseDto] to a [Fact] model.
 *
 * @return A [Fact] with the fact and length from the [FactResponseDto].
 */
fun FactResponseDto.toModel(): Fact = Fact(
    fact = fact,
    length = length
)
