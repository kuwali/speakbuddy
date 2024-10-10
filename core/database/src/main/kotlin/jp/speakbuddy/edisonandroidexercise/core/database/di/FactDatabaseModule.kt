package jp.speakbuddy.edisonandroidexercise.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.core.database.FactDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FactDatabaseModule {

    @Provides
    @Singleton
    fun providesFactDatabase(
        @ApplicationContext context: Context,
    ): FactDatabase = Room.databaseBuilder(
        context,
        FactDatabase::class.java,
        "fact-database",
    ).build()

}