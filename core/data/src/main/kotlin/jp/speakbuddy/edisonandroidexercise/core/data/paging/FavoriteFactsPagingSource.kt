package jp.speakbuddy.edisonandroidexercise.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject
import javax.inject.Singleton
import jp.speakbuddy.edisonandroidexercise.core.data.model.toModel
import jp.speakbuddy.edisonandroidexercise.core.database.dao.FactDao
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.first

@Singleton
class FavoriteFactsPagingSource
@Inject
constructor(
    private val factDao: FactDao
) : PagingSource<Int, Fact>() {
    companion object {
        private const val INITIAL_PAGE = 0
    }

    /**
     * Loads a page of favorite facts from the database.
     *
     * @param params The loading parameters including the page key and load size.
     * @return A [LoadResult] containing the loaded facts, previous and next page keys.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Fact> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val pageSize = params.loadSize

            val facts = factDao.getFavoriteFacts(page * pageSize, pageSize).first()

            LoadResult.Page(
                data = facts.map { it.toModel() },
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (facts.size == pageSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Fact>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
