package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

private const val ANIMATION_DURATION_MILLIS = 300
private const val MAX_LINES_FACT_TEXT = 2

/**
 * Composable function that displays the Favorites Fact screen.
 *
 * This screen shows a list of favorite facts, handles loading and error states,
 * and provides functionality to toggle favorite status of facts.
 *
 * @param viewModel The ViewModel that provides data and handles business logic for this screen.
 */
@Composable
fun FavoritesFactScreen(
    viewModel: FavoritesFactViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteFacts = viewModel.favoriteFacts.collectAsLazyPagingItems()
    val animatingItems by remember { viewModel.animatingItems }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    when (uiState) {
        is UiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
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

@Composable
fun FavoriteFactsList(
    favoriteFacts: LazyPagingItems<Fact>,
    animatingItems: Set<String>,
    onToggleFavorite: (Fact) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(DesignR.dimen.core_designsystem_padding_default)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(DesignR.dimen.core_designsystem_padding_default))
    ) {
        items(
            count = favoriteFacts.itemCount,
            key = { index -> favoriteFacts[index]?.fact ?: index }
        ) { index ->
            favoriteFacts[index]?.let { fact ->
                AnimatedVisibility(
                    visible = !animatingItems.contains(fact.fact),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS)) +
                            fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS))
                ) {
                    FavoriteFactCard(
                        fact = fact,
                        onUnfavorite = { onToggleFavorite(fact) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyFavoritesList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.feature_favoritefact_no_favorites),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(DesignR.dimen.core_designsystem_padding_small)))
        Text(
            text = stringResource(R.string.feature_favoritefact_add_favorites_prompt),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorState(errorMessage: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(dimensionResource(DesignR.dimen.core_designsystem_padding_default)))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.feature_favoritefact_retry))
        }
    }
}

@Composable
fun FavoriteFactCard(
    fact: Fact,
    onUnfavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.feature_favoritefact_spacing_extra_small))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(DesignR.dimen.core_designsystem_padding_default)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onUnfavorite,
                modifier = Modifier.size(dimensionResource(R.dimen.feature_favoritefact_icon_size_medium))
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.feature_favoritefact_unfavorite),
                    tint = Color.Red
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(DesignR.dimen.core_designsystem_padding_default)))
            Column {
                Text(
                    text = fact.fact,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = MAX_LINES_FACT_TEXT,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.feature_favoritefact_spacing_extra_small)))
                Text(
                    text = stringResource(R.string.feature_favoritefact_length_prefix, fact.length),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

