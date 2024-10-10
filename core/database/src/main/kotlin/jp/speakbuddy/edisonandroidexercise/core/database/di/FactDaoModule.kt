package jp.speakbuddy.edisonandroidexercise.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.core.database.FactDatabase
import jp.speakbuddy.edisonandroidexercise.core.database.dao.FactDao

@Module
@InstallIn(SingletonComponent::class)
internal object FactDaoModule {
    @Provides
    fun providesFactDao(database: FactDatabase): FactDao = database.factDao()
}
