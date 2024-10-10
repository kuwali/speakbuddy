package jp.speakbuddy.edisonandroidexercise.core.network

import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto

interface FactNetworkDataSource {
    /**
     * Fetches a fact from the network.
     *
     * @return A [FactResponseDto] containing the fetched fact.
     */
    suspend fun getFact(): FactResponseDto
}
