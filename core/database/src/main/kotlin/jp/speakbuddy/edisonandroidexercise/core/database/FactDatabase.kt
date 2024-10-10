package jp.speakbuddy.edisonandroidexercise.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.speakbuddy.edisonandroidexercise.core.database.dao.FactDao
import jp.speakbuddy.edisonandroidexercise.core.database.model.FactEntity

@Database(entities = [FactEntity::class], version = 1)
internal abstract class FactDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}
