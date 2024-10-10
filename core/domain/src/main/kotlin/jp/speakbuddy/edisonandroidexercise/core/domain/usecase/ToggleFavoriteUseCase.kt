package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact

interface ToggleFavoriteUseCase {
    suspend operator fun invoke(fact: Fact)
}

class ToggleFavoriteUseCaseImpl
@Inject
constructor(
    private val repository: FactRepository
) : ToggleFavoriteUseCase {
    override suspend operator fun invoke(fact: Fact) {
        repository.toggleFavorite(fact)
    }
}
