package jp.speakbuddy.edisonandroidexercise.core.domain.repository

import androidx.paging.PagingData
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    /**
     * Retrieves a single fact.
     *
     * @param forceRefresh If true, forces a refresh of the data instead of using cached data.
     * @return A [Flow] emitting a single [Fact].
     */
    fun getFact(forceRefresh: Boolean): Flow<Fact>

    /**
     * Toggles the favorite status of a given fact.
     *
     * @param fact The [Fact] object whose favorite status is to be toggled.
     */
    suspend fun toggleFavorite(fact: Fact)

    /**
     * Retrieves a flow of paged favorite facts.
     *
     * @return A [Flow] emitting [PagingData] of favorite [Fact] objects.
     */
    fun getFavoriteFacts(): Flow<PagingData<Fact>>
}
