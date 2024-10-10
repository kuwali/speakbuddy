package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui

import androidx.paging.PagingData
import java.util.stream.Stream
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.GetFavoriteFactsUseCase
import jp.speakbuddy.edisonandroidexercise.core.domain.usecase.ToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class FavoritesFactViewModelTest {
    private val getFavoriteFactsUseCase: GetFavoriteFactsUseCase = mock()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mock()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FavoritesFactViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel =
            FavoritesFactViewModel(
                getFavoriteFactsUseCase = getFavoriteFactsUseCase,
                toggleFavoriteUseCase = toggleFavoriteUseCase,
                dispatcher = testDispatcher
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when ViewModel is initialized, uiState should be Loading`() = runTest {
        // When
        val initialState = viewModel.uiState.first()

        // Then
        Assertions.assertTrue(initialState is UiState.Loading)
    }

    @ParameterizedTest
    @MethodSource("provideSuccessTestData")
    fun `when loadFavoriteFacts succeeds, uiState should be updated to Success`(facts: List<Fact>) =
        runTest {
            // Given
            val pagingData = PagingData.from(facts)
            `when`(getFavoriteFactsUseCase()).thenReturn(flowOf(pagingData))

            // When
            viewModel.initialize()
            advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.first()
            Assertions.assertTrue(finalState is UiState.Success)
            val favoriteFacts = viewModel.favoriteFacts.first()
            Assertions.assertNotNull(favoriteFacts)
            Assertions.assertTrue(favoriteFacts != PagingData.empty<Fact>())
        }

    @ParameterizedTest
    @MethodSource("provideErrorTestData")
    fun `when loadFavoriteFacts fails, uiState should be updated to Error`(
        error: Throwable,
        expectedMessage: String
    ) = runTest {
        // Given
        `when`(getFavoriteFactsUseCase()).thenThrow(error)

        // When
        viewModel.initialize()
        advanceUntilIdle()

        // Then
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Error)
        Assertions.assertEquals(expectedMessage, (finalState as UiState.Error).message)
    }

    @Test
    fun `when toggleFavorite is called, it should update animatingItems`() = runTest {
        // Given
        val testFact = Fact("Test fact", 9)

        // When
        viewModel.toggleFavorite(testFact)
        advanceUntilIdle()

        // Then
        val animatingItems = viewModel.animatingItems.first()
        Assertions.assertTrue(testFact.fact !in animatingItems)
        verify(toggleFavoriteUseCase).invoke(testFact)
    }

    @Test
    fun `when toggleFavorite fails, uiState should be updated to Error`() = runTest {
        // Given
        val testFact = Fact("Test fact", 9)
        `when`(toggleFavoriteUseCase(testFact)).thenThrow(RuntimeException("Toggle error"))

        // When
        viewModel.toggleFavorite(testFact)
        advanceUntilIdle()

        // Then
        val finalState = viewModel.uiState.first()
        Assertions.assertTrue(finalState is UiState.Error)
        Assertions.assertEquals(
            "Failed to toggle favorite: Toggle error",
            (finalState as UiState.Error).message
        )
    }

    companion object {
        @JvmStatic
        fun provideSuccessTestData() = Stream.of(
            Arguments.of(listOf(Fact("Test fact 1", 1), Fact("Test fact 2", 2))),
            Arguments.of(listOf(Fact("Single fact", 1))),
            Arguments.of(emptyList<Fact>())
        )

        @JvmStatic
        fun provideErrorTestData() = Stream.of(
            Arguments.of(
                RuntimeException("Test error"),
                "Failed to load favorite facts: Test error"
            ),
            Arguments.of(
                IllegalStateException("Another error"),
                "Failed to load favorite facts: Another error"
            )
        )
    }
}
