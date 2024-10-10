package jp.speakbuddy.edisonandroidexercise.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "fact") val fact: String,
    @ColumnInfo(name = "length") val length: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)
