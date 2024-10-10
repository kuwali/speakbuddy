package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.speakbuddy.edisonandroidexercise.core.common.network.Dispatcher
import jp.speakbuddy.edisonandroidexercise.core.common.network.SBDispatchers
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFavoriteFactsUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting

private const val TOGGLE_ANIMATION_DELAY = 300L

/**
 * ViewModel for managing favorite facts.
 *
 * This ViewModel handles the loading, displaying, and toggling of favorite facts.
 * It uses Paging 3 library for efficient loading of large datasets.
 *
 * @property getFavoriteFactsUseCase Use case for retrieving favorite facts.
 * @property toggleFavoriteUseCase Use case for toggling the favorite status of a fact.
 * @property dispatcher Coroutine dispatcher for IO operations.
 */
@HiltViewModel
class FavoritesFactViewModel
@Inject
constructor(
    private val getFavoriteFactsUseCase: GetFavoriteFactsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    @Dispatcher(SBDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _favoriteFacts = MutableStateFlow<PagingData<Fact>>(PagingData.empty())
    val favoriteFacts: StateFlow<PagingData<Fact>> = _favoriteFacts.asStateFlow()

    private val _animatingItems = MutableStateFlow<Set<String>>(emptySet())
    val animatingItems = _animatingItems.asStateFlow()

    fun initialize() {
        loadFavoriteFacts()
    }

    /**
     * Loads favorite facts using the [GetFavoriteFactsUseCase].
     *
     * This function fetches favorite facts, caches them in the [viewModelScope],
     * and updates the [_favoriteFacts] and [_uiState] accordingly.
     * If an error occurs during loading, it updates the [_uiState] with an error message.
     */
    private fun loadFavoriteFacts() {
        viewModelScope.launch(dispatcher) {
            try {
                getFavoriteFactsUseCase().cachedIn(viewModelScope).collect { pagingData ->
                    _favoriteFacts.value = pagingData
                    _uiState.value = UiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load favorite facts: ${e.message}")
            }
        }
    }

    /**
     * Toggles the favorite status of a fact.
     *
     * This function performs the following steps:
     * 1. Adds the fact to animating items.
     * 2. Delays to allow animation to complete.
     * 3. Toggles the favorite status using [toggleFavoriteUseCase].
     * 4. Removes the fact from animating items.
     * 5. Reloads favorite facts.
     *
     * If an error occurs during the process, it updates the [_uiState] with an error message.
     *
     * @param fact The fact to toggle the favorite status for.
     */
    fun toggleFavorite(fact: Fact) {
        viewModelScope.launch(dispatcher) {
            try {
                // Add the fact to animating items
                _animatingItems.value += fact.fact

                // Delay to allow animation to complete
                delay(TOGGLE_ANIMATION_DELAY)

                // Actually toggle the favorite
                toggleFavoriteUseCase(fact)

                // Remove the fact from animating items
                _animatingItems.value -= fact.fact

                // Reload favorite facts after toggling
                loadFavoriteFacts()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to toggle favorite: ${e.message}")
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

    data object Success : UiState()

    data class Error(val message: String) : UiState()
}
