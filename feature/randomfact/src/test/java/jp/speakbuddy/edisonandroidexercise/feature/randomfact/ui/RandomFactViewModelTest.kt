package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui

import jp.speakbuddy.edisonandroidexercise.core.common.testing.MainDispatcherRule
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFactUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class RandomFactViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getFactUseCase: GetFactUseCase = mock()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mock()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: RandomFactViewModel

    @BeforeEach
    fun setup() {
        viewModel = RandomFactViewModel(
            getFactUseCase = getFactUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
            dispatcher = testDispatcher,
        )
    }

    @Test
    fun `when ViewModel is initialized, uiState should be Loading`() = runTest {
        // When
        val initialState = viewModel.uiState.first()

        // Then
        Assertions.assertTrue(initialState is UiState.Loading)
    }

    @Test
    fun `when loadFact succeeds, uiState should be updated to Success`() = runTest {
        // Given
        val testFact = Fact("Test fact about cats", 21)
        doReturn(testFact).`when`(getFactUseCase).invoke(anyBoolean())

        // When
        viewModel.updateFact()

        // Then
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Success)
        with(finalState as UiState.Success) {
            Assertions.assertEquals("Test fact about cats", fact)
            Assertions.assertEquals(21, factLength)
            Assertions.assertTrue(containsMultipleCats)
        }
    }

    @Test
    fun `when loadFact fails, uiState should be updated to Error`() = runTest {
        // Given
        doThrow(RuntimeException("Test error")).`when`(getFactUseCase).invoke(anyBoolean())

        // When
        viewModel.updateFact()

        // Then
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Error)
        Assertions.assertEquals(
            "Failed to update fact. Error: Test error",
            (finalState as UiState.Error).message
        )
    }

    @Test
    fun `when loadFact succeeds with single cat, containsMultipleCats should be false`() = runTest {
        // Given
        val testFact = Fact("Test fact about cat", 21)
        doReturn(testFact).`when`(getFactUseCase).invoke(anyBoolean())

        // When
        viewModel.updateFact()

        // Then
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Success)
        with(finalState as UiState.Success) {
            Assertions.assertFalse(containsMultipleCats)
        }
    }

    @Test
    fun `when initial loadFact fails, error message should differ from update error message`() =
        runTest {
            // Given
            doThrow(RuntimeException("Test error")).`when`(getFactUseCase).invoke(anyBoolean())

            // When
            viewModel.initialize()

            // Then
            val finalState = viewModel.uiState.first()
            Assertions.assertTrue(finalState is UiState.Error)
            Assertions.assertEquals(
                "Failed to load initial fact. Error: Test error",
                (finalState as UiState.Error).message
            )
        }

    @Test
    fun `when toggleFavorite is called, it should update the favorite status`() = runTest {
        // Given
        val initialFact = Fact("Test fact about cats", 21, isFavorite = false)
        doReturn(initialFact).`when`(getFactUseCase).invoke(anyBoolean())
        viewModel.initialize()

        // When
        viewModel.toggleFavorite()

        // Then
        verify(toggleFavoriteUseCase).invoke(initialFact)
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Success)
        Assertions.assertTrue((finalState as UiState.Success).isFavorite)
    }

    @Test
    fun `when toggleFavorite is called without a current fact, nothing should happen`() = runTest {
        // When
        viewModel.toggleFavorite()

        // Then
        verifyNoInteractions(toggleFavoriteUseCase)
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Loading)
    }
}
