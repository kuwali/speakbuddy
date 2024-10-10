package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.fake

import androidx.paging.PagingData
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFavoriteFactsUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetFavoriteFactsUseCase : GetFavoriteFactsUseCase {
    private var fakeFacts: List<Fact> = emptyList()

    fun setFakeFacts(facts: List<Fact>) {
        fakeFacts = facts
    }

    override fun invoke(): Flow<PagingData<Fact>> {
        return flowOf(PagingData.from(fakeFacts))
    }
}
