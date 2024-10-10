package jp.speakbuddy.edisonandroidexercise.core.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFactUseCaseImpl
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFavoriteFactsUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFavoriteFactsUseCaseImpl
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FactDomainModule {

    @Binds
    abstract fun bindGetFactUseCase(
        getFactUseCaseImpl: GetFactUseCaseImpl
    ): GetFactUseCase

    @Binds
    abstract fun bindToggleFavoriteUseCase(
        toggleFavoriteUseCaseImpl: ToggleFavoriteUseCaseImpl
    ): ToggleFavoriteUseCase

    @Binds
    abstract fun bindGetFavoriteFactsUseCase(
        getFavoriteFactsUseCaseImpl: GetFavoriteFactsUseCaseImpl
    ): GetFavoriteFactsUseCase
}