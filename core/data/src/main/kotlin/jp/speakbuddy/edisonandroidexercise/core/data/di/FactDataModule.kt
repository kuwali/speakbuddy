package jp.speakbuddy.edisonandroidexercise.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import jp.speakbuddy.edisonandroidexercise.core.data.repository.FactRepositoryImpl
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class FactDataModule {
    @Binds
    @Singleton
    abstract fun bindFactRepository(factRepositoryImpl: FactRepositoryImpl): FactRepository
}
