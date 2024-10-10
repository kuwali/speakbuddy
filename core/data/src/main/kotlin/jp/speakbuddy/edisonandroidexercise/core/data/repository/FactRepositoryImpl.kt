package jp.speakbuddy.edisonandroidexercise.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import jp.speakbuddy.edisonandroidexercise.core.data.model.toEntity
import jp.speakbuddy.edisonandroidexercise.core.data.model.toModel
import jp.speakbuddy.edisonandroidexercise.core.data.paging.FavoriteFactsPagingSource
import jp.speakbuddy.edisonandroidexercise.core.database.dao.FactDao
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.core.network.FactNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FactRepositoryImpl @Inject constructor(
    private val favoriteFactsPagingSource: FavoriteFactsPagingSource,
    private val networkDataSource: FactNetworkDataSource,
    private val dao: FactDao,
) : FactRepository {
    override fun getFact(forceRefresh: Boolean): Flow<Fact> = flow {
        if (forceRefresh) {
            val remoteFact = networkDataSource.getFact()
            dao.insertFact(remoteFact.toEntity())
            emit(remoteFact.toModel())
        } else {
            val localFact = dao.getLatestFact().firstOrNull()
            if (localFact != null) {
                emit(localFact.toModel())
            } else {
                val remoteFact = networkDataSource.getFact()
                dao.insertFact(remoteFact.toEntity())
                emit(remoteFact.toModel())
            }
        }
    }

    override suspend fun toggleFavorite(fact: Fact) {
        dao.updateFavoriteStatus(fact.fact, !fact.isFavorite)
    }

    override fun getFavoriteFacts(): Flow<PagingData<Fact>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { favoriteFactsPagingSource }
        ).flow
    }
}
