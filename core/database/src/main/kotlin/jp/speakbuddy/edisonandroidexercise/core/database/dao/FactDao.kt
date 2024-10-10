package jp.speakbuddy.edisonandroidexercise.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.speakbuddy.edisonandroidexercise.core.database.model.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Query("SELECT * FROM facts ORDER BY id DESC LIMIT 1")
    fun getLatestFact(): Flow<FactEntity?>

    @Insert
    suspend fun insertFact(fact: FactEntity)

    @Query("UPDATE facts SET is_favorite = :isFavorite WHERE fact = :fact")
    suspend fun updateFavoriteStatus(fact: String, isFavorite: Boolean)

    @Query("SELECT * FROM facts WHERE is_favorite = 1 ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun getFavoriteFacts(offset: Int, limit: Int): Flow<List<FactEntity>>
}
