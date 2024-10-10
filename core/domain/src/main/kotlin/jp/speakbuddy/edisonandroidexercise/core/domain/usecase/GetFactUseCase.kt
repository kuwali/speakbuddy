package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.Flow

interface GetFactUseCase {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Fact>
}

class GetFactUseCaseImpl
@Inject
constructor(
    private val repository: FactRepository
) : GetFactUseCase {
    override operator fun invoke(forceRefresh: Boolean): Flow<Fact> {
        return repository.getFact(forceRefresh)
    }
}
