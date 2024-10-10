package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.R
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view.EmptyFavoritesList
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view.ErrorState
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view.FavoriteFactsList

/**
 * Composable function that displays the Favorites Fact screen.
 *
 * This screen shows a list of favorite facts, handles loading and error states,
 * and provides functionality to toggle favorite status of facts.
 *
 * @param viewModel The ViewModel that provides data and handles business logic for this screen.
 */
@Composable
fun FavoritesFactScreen(viewModel: FavoritesFactViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteFacts = viewModel.favoriteFacts.collectAsLazyPagingItems()
    val animatingItems by remember { viewModel.animatingItems }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    when (uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .testTag(stringResource(R.string.feature_favoritefact_loading_tag))
            )
        }

        is UiState.Success -> {
            if (favoriteFacts.itemCount == 0) {
                EmptyFavoritesList()
            } else {
                FavoriteFactsList(favoriteFacts, animatingItems, viewModel::toggleFavorite)
            }
        }

        is UiState.Error -> {
            ErrorState(
                errorMessage = (uiState as UiState.Error).message,
                onRetry = { viewModel.initialize() }
            )
        }
    }
}
