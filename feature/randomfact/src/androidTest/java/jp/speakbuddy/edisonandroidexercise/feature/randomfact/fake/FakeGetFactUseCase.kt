package jp.speakbuddy.edisonandroidexercise.feature.randomfact.fake

import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull

class FakeGetFactUseCase : GetFactUseCase {
    private val _fact = MutableStateFlow<Fact?>(null)
    val fact: StateFlow<Fact?> = _fact

    fun setFact(fact: Fact) {
        _fact.value = fact
    }

    override fun invoke(forceRefresh: Boolean): Flow<Fact> {
        return _fact.filterNotNull()
    }

    fun clear() {
        _fact.value = null
    }
}
