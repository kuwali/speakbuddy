package jp.speakbuddy.edisonandroidexercise.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import jp.speakbuddy.edisonandroidexercise.core.network.BuildConfig
import jp.speakbuddy.edisonandroidexercise.core.network.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.core.network.retrofit.FactApi
import jp.speakbuddy.edisonandroidexercise.core.network.retrofit.FactRemoteDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class FactNetworkModule {
    companion object {
        @Provides
        @Singleton
        fun provideFactApi(): FactApi {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(FactApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindFactNetworkDataSource(
        factRemoteDataSource: FactRemoteDataSource
    ): FactNetworkDataSource
}
