package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view.FactContent
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view.FactInfoCards
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view.LoadingOverlay
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view.UpdateButton
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

/**
 * Composable function that displays the Random Fact screen.
 *
 * This screen shows a random fact, allows the user to favorite it, and provides
 * additional information about the fact. It also includes a button to fetch a new fact.
 *
 * @param viewModel The ViewModel that manages the UI state and business logic for this screen.
 */
@Composable
fun RandomFactScreen(
    viewModel: RandomFactViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(DesignR.dimen.core_designsystem_padding_default))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FactContent(
                uiState = uiState,
                onToggleFavorite = { viewModel.toggleFavorite() }
            )
            Spacer(modifier = Modifier.height(dimensionResource(DesignR.dimen.core_designsystem_padding_default)))
            FactInfoCards(uiState)
        }

        UpdateButton(
            onUpdateClick = { viewModel.updateFact() },
            enabled = uiState !is UiState.Loading,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        LoadingOverlay(isVisible = uiState is UiState.Loading)
    }
}
