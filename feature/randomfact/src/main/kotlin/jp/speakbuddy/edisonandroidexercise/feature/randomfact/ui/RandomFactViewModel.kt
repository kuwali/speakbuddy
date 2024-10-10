package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.core.common.network.Dispatcher
import jp.speakbuddy.edisonandroidexercise.core.common.network.SBDispatchers
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

/**
 * ViewModel for managing random cat facts.
 *
 * This ViewModel handles loading, updating, and toggling favorite status of cat facts.
 * It exposes the current UI state through a StateFlow and provides methods to interact
 * with the facts.
 *
 * @property getFactUseCase Use case for retrieving cat facts
 * @property toggleFavoriteUseCase Use case for toggling the favorite status of a fact
 * @property dispatcher Coroutine dispatcher for IO operations
 */
@HiltViewModel
class RandomFactViewModel
@Inject
constructor(
    private val getFactUseCase: GetFactUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    @Dispatcher(SBDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentFact: Fact? = null

    fun initialize() {
        loadFact(forceRefresh = false)
    }

    fun updateFact() {
        loadFact(forceRefresh = true)
    }

    /**
     * Toggles the favorite status of the current fact.
     *
     * This function launches a coroutine to toggle the favorite status of the current fact
     * using the [toggleFavoriteUseCase]. It then updates the [currentFact] with the new
     * favorite status and refreshes the UI state.
     */
    fun toggleFavorite() {
        viewModelScope.launch(dispatcher) {
            currentFact?.let { fact ->
                toggleFavoriteUseCase(fact)
                currentFact = fact.copy(isFavorite = !fact.isFavorite)
                updateUiState()
            }
        }
    }

    /**
     * Updates the UI state based on the current fact.
     *
     * This private function updates the [_uiState] with a new [UiState.Success] instance
     * if a current fact exists. It extracts relevant information from the fact and
     * sets the UI state accordingly.
     */
    private fun updateUiState() {
        currentFact?.let { fact ->
            _uiState.value =
                UiState.Success(
                    fact = fact.fact,
                    factLength = fact.length,
                    containsMultipleCats = fact.fact.contains("cats", ignoreCase = true),
                    isFavorite = fact.isFavorite
                )
        }
    }

    /**
     * Loads a fact, either from cache or by fetching a new one.
     *
     * This function launches a coroutine to load a fact using the [getFactUseCase].
     * It updates the UI state to [UiState.Loading] while fetching, then updates with
     * the new fact or an error state if the operation fails.
     *
     * @param forceRefresh If true, forces a refresh of the fact, ignoring any cached data.
     */
    private fun loadFact(forceRefresh: Boolean) {
        viewModelScope.launch(dispatcher) {
            _uiState.value = UiState.Loading
            try {
                getFactUseCase(forceRefresh).collect { fact ->
                    currentFact = fact
                    updateUiState()
                }
            } catch (e: Throwable) {
                val state = if (forceRefresh) "update" else "load initial"
                _uiState.value =
                    UiState.Error("Failed to $state fact. Error: ${e.message}")
            }
        }
    }

    @VisibleForTesting
    fun setTestUiState(state: UiState) {
        _uiState.value = state
    }
}

sealed class UiState {
    data object Loading : UiState()

    data class Success(
        val fact: String,
        val factLength: Int,
        val containsMultipleCats: Boolean,
        val isFavorite: Boolean
    ) : UiState()

    data class Error(val message: String) : UiState()
}
