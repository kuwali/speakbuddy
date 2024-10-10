package jp.speakbuddy.edisonandroidexercise.feature.favoritefact

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.speakbuddy.edisonandroidexercise.core.uitesting.HiltTestingComponentActivity
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.fake.FakeGetFavoriteFactsUseCase
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.fake.FakeToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.FavoritesFactScreen
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.FavoritesFactViewModel
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.UiState
import kotlin.test.Test
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FavoritesFactScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestingComponentActivity>()

    private val getFavoriteFactsUseCase = FakeGetFavoriteFactsUseCase()
    private val toggleFavoriteUseCase = FakeToggleFavoriteUseCase()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FavoritesFactViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        viewModel =
            FavoritesFactViewModel(
                getFavoriteFactsUseCase = getFavoriteFactsUseCase,
                toggleFavoriteUseCase = toggleFavoriteUseCase,
                dispatcher = testDispatcher
            )

        composeTestRule.setContent {
            FavoritesFactScreen(viewModel = viewModel)
        }
    }

    @Test
    fun loadingState_isDisplayed_initially() {
        // Given
        viewModel.setTestUiState(UiState.Loading)

        // Then
        composeTestRule.onNodeWithTag("loading_tag").assertIsDisplayed()
    }

    @Test
    fun errorState_isDisplayed_onError() {
        // Given
        val errorMessage = "Failed to load favorite facts"
        viewModel.setTestUiState(UiState.Error(errorMessage))

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun emptyState_isDisplayed_whenNoFavorites() {
        // Given
        getFavoriteFactsUseCase.setFakeFacts(emptyList())
        viewModel.initialize()
        viewModel.setTestUiState(UiState.Success)

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("No favorite facts yet").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add some facts to your favorites!").assertIsDisplayed()
    }
}
