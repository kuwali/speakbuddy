package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.fake

import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeToggleFavoriteUseCase : ToggleFavoriteUseCase {
    private val _toggledFacts = MutableStateFlow<List<Fact>>(emptyList())
    val toggledFacts: StateFlow<List<Fact>> = _toggledFacts

    override suspend fun invoke(fact: Fact) {
        val currentList = _toggledFacts.value.toMutableList()
        if (currentList.contains(fact)) {
            currentList.remove(fact)
        } else {
            currentList.add(fact)
        }
        _toggledFacts.value = currentList
    }

    fun reset() {
        _toggledFacts.value = emptyList()
    }
}
