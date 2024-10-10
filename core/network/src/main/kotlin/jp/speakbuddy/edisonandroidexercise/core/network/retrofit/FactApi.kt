package jp.speakbuddy.edisonandroidexercise.core.network.retrofit

import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    suspend fun getFact(): FactResponseDto
}
