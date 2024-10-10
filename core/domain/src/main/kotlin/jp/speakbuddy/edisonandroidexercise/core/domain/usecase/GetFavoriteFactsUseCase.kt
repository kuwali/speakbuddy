package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.Flow

interface GetFavoriteFactsUseCase {
    operator fun invoke(): Flow<PagingData<Fact>>
}

class GetFavoriteFactsUseCaseImpl
@Inject
constructor(
    private val repository: FactRepository
) : GetFavoriteFactsUseCase {
    override operator fun invoke(): Flow<PagingData<Fact>> {
        return repository.getFavoriteFacts()
    }
}
