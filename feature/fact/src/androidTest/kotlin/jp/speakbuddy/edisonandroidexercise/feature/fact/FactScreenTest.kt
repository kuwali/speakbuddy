package jp.speakbuddy.edisonandroidexercise.feature.fact

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.speakbuddy.edisonandroidexercise.core.uitesting.HiltTestingComponentActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FactScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestingComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            FactScreen()
        }
    }

    @Test
    fun topAppBar_isDisplayed() {
        composeTestRule
            .onNodeWithText("Cat Facts")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun bothTabs_areDisplayed() {
        composeTestRule
            .onNodeWithText("Random Fact")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Favorites")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun initialTab_isRandomFact() {
        composeTestRule
            .onNodeWithText("Random Fact")
            .assertIsSelected()
    }

    @Test
    fun clickingFavoritesTab_switchesScreen() {
        // Start at Random Fact tab
        composeTestRule
            .onNodeWithText("Random Fact")
            .assertIsSelected()

        // Click on Favorites tab
        composeTestRule
            .onNodeWithText("Favorites")
            .performClick()

        // Assert that the Favorites tab is now selected
        composeTestRule
            .onNodeWithText("Favorites")
            .assertIsSelected()

        // Click on Random Fact tab
        composeTestRule
            .onNodeWithText("Random Fact")
            .performClick()

        // Assert that the Random Fact tab is now selected
        composeTestRule
            .onNodeWithText("Random Fact")
            .assertIsSelected()
    }
}
