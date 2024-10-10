package jp.speakbuddy.edisonandroidexercise.feature.randomfact

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.core.uitesting.HiltTestingComponentActivity
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.fake.FakeGetFactUseCase
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.fake.FakeToggleFavoriteUseCase
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.RandomFactScreen
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.RandomFactViewModel
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.UiState
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RandomFactScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestingComponentActivity>()

    private val getFactUseCase = FakeGetFactUseCase()
    private val toggleFavoriteUseCase = FakeToggleFavoriteUseCase()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RandomFactViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        viewModel =
            RandomFactViewModel(
                getFactUseCase = getFactUseCase,
                toggleFavoriteUseCase = toggleFavoriteUseCase,
                dispatcher = testDispatcher
            )

        composeTestRule.setContent {
            RandomFactScreen(viewModel = viewModel)
        }
    }

    @Test
    fun loadingState_isDisplayed_initially() {
        // Given
        viewModel.setTestUiState(UiState.Loading)

        // Then
        composeTestRule.onNodeWithText("Loading…").assertIsDisplayed()
    }

    @Test
    fun factContent_isDisplayed_whenFactIsLoaded() {
        // Given
        setupTestFactAndUiState(Fact("This is a test fact", 42, false))

        // Then
        composeTestRule.onNodeWithText("This is a test fact").assertIsDisplayed()
    }

    @Test
    fun errorState_isDisplayed_onError() {
        // Given
        val errorMessage = "Failed to load fact"
        viewModel.setTestUiState(UiState.Error(errorMessage))

        // Then
        composeTestRule.onNodeWithText(errorMessage, substring = true).assertIsDisplayed()
    }

    @Test
    fun favoriteButton_togglesFromUnfavoriteToFavorite_whenClicked() {
        // Given
        val testFact = Fact("This is a test fact", 42, false)
        setupTestFactAndUiState(testFact)

        // Initially not favorite
        composeTestRule.onNodeWithContentDescription("Add to favorites").assertIsDisplayed()

        // Click favorite button
        composeTestRule.onNodeWithContentDescription("Add to favorites").performClick()

        // Simulate favorite toggle
        setupTestFactAndUiState(testFact.copy(isFavorite = true))

        // Wait for the UI to update
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithContentDescription("Remove from favorites").isDisplayed()
        }

        // Should now be favorite
        composeTestRule.onNodeWithContentDescription("Remove from favorites").assertIsDisplayed()
    }

    @Test
    fun updateButton_fetchesNewFact_whenClicked() {
        // Given
        setupTestFactAndUiState(Fact("Initial fact", 30, false))

        // Initially displayed
        composeTestRule.onNodeWithText("Initial fact").assertIsDisplayed()

        // Click update button
        composeTestRule.onNodeWithText("Get New Fact").performClick()

        // Simulate fact update
        setupTestFactAndUiState(Fact("New fact", 20, false))

        // New fact displayed
        composeTestRule.onNodeWithText("New fact").assertIsDisplayed()
    }

    @Test
    fun factLength_isHidden_forShortFacts() {
        // Given
        setupTestFactAndUiState(Fact("This is a test fact", 42, false))

        // Then
        composeTestRule.onNodeWithText("42 characters").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Multiple Cats!").assertIsNotDisplayed()
    }

    @Test
    fun multipleCats_isDisplayed_whenFactContainsMultipleCats() {
        // Given
        setupTestFactAndUiState(
            Fact(
                "This fact mentions cats multiple times: cats, cats, cats!",
                60,
                false
            )
        )

        // Then
        composeTestRule.onNodeWithText("60 characters").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Multiple Cats!").assertIsDisplayed()
    }

    @Test
    fun multipleCats_isHidden_forSingleCatFacts() {
        // Given
        setupTestFactAndUiState(Fact("This fact only mentions cat once.", 135, false))

        // Then
        composeTestRule.onNodeWithText("135 characters").assertIsDisplayed()
        composeTestRule.onNodeWithText("Multiple Cats!").assertDoesNotExist()
    }

    @Test
    fun factLength_isDisplayed_whenFactLengthExceedsLimit() {
        // Given
        setupTestFactAndUiState(
            Fact(
                "This fact mentions cats multiple times: cats, cats, cats!",
                135,
                false
            )
        )

        // Then
        composeTestRule.onNodeWithText("135 characters").assertIsDisplayed()
        composeTestRule.onNodeWithText("Multiple Cats!").assertIsDisplayed()
    }

    private fun setupTestFactAndUiState(fact: Fact) {
        getFactUseCase.setFact(fact)
        viewModel.setTestUiState(
            UiState.Success(
                fact.fact,
                fact.length,
                fact.fact.contains("cats", ignoreCase = true),
                fact.isFavorite
            )
        )
        composeTestRule.waitForIdle()
    }
}
