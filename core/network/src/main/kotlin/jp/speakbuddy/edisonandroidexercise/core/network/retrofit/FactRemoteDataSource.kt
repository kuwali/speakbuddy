package jp.speakbuddy.edisonandroidexercise.core.network.retrofit

import jp.speakbuddy.edisonandroidexercise.core.network.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto
import javax.inject.Inject

class FactRemoteDataSource @Inject constructor(
    private val factApi: FactApi
) : FactNetworkDataSource {
    override suspend fun getFact(): FactResponseDto = factApi.getFact()
}