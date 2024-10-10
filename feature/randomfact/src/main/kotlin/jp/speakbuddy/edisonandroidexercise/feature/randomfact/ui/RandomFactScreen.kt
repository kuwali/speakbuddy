package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

private const val MIN_SHOW_CHAR_LENGTH = 100

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


@Composable
private fun FactContent(uiState: UiState, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.feature_randomfact_card_corner_radius)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.feature_randomfact_card_elevation))
    ) {
        Box(
            modifier = Modifier.padding(dimensionResource(DesignR.dimen.core_designsystem_padding_default)),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is UiState.Error -> ErrorMessage(uiState.message)
                is UiState.Success -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.fact,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(DesignR.dimen.core_designsystem_padding_small)))

                        FavoriteButton(
                            isFavorite = uiState.isFavorite,
                            onToggleFavorite = onToggleFavorite
                        )
                    }
                }

                is UiState.Loading -> Text(
                    stringResource(R.string.feature_randomfact_loading),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun FavoriteButton(isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    IconButton(onClick = onToggleFavorite) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) {
                stringResource(R.string.feature_randomfact_remove_from_favorites)
            } else {
                stringResource(R.string.feature_randomfact_add_to_favorites)
            },
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}


@Composable
private fun FactInfoCards(uiState: UiState) {
    if (uiState !is UiState.Success) return

    val showCharLength = remember(uiState) { uiState.factLength > MIN_SHOW_CHAR_LENGTH }
    val showMultipleCats = remember(uiState) { uiState.containsMultipleCats }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (showCharLength || showMultipleCats) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (showCharLength) {
                        InfoCard(
                            text = stringResource(
                                R.string.feature_randomfact_characters_suffix,
                                uiState.factLength
                            ),
                            cardType = if (showMultipleCats) InfoCardType.DoubleFirst else InfoCardType.Single
                        )
                    }
                    if (showMultipleCats) {
                        InfoCard(
                            text = stringResource(R.string.feature_randomfact_multiple_cats),
                            cardType = if (showCharLength) InfoCardType.DoubleSecond else InfoCardType.Single,
                            textColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

sealed class InfoCardType {
    data object Single : InfoCardType()
    data object DoubleFirst : InfoCardType()
    data object DoubleSecond : InfoCardType()
}

@Composable
private fun InfoCard(
    text: String,
    cardType: InfoCardType,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Card(
        modifier = Modifier
            .infoCardWidth(cardType)
            .infoCardPadding(cardType),
        shape = RoundedCornerShape(dimensionResource(R.dimen.feature_randomfact_info_card_corner_radius)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.feature_randomfact_info_card_elevation)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(dimensionResource(DesignR.dimen.core_designsystem_padding_small))
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Modifier.infoCardWidth(cardType: InfoCardType): Modifier {
    return this.then(
        when (cardType) {
            is InfoCardType.Single -> this.width(dimensionResource(R.dimen.feature_randomfact_info_card_width_single))
            is InfoCardType.DoubleFirst,
            is InfoCardType.DoubleSecond ->
                this.width(dimensionResource(R.dimen.feature_randomfact_info_card_width_double))
        }
    )
}

@Composable
private fun Modifier.infoCardPadding(cardType: InfoCardType): Modifier {
    return this.then(
        this.padding(
            start = when (cardType) {
                is InfoCardType.Single,
                is InfoCardType.DoubleFirst -> dimensionResource(DesignR.dimen.core_designsystem_padding_zero)

                is InfoCardType.DoubleSecond -> dimensionResource(DesignR.dimen.core_designsystem_padding_small)
            },
            end = when (cardType) {
                is InfoCardType.Single,
                is InfoCardType.DoubleSecond -> dimensionResource(DesignR.dimen.core_designsystem_padding_zero)

                is InfoCardType.DoubleFirst -> dimensionResource(DesignR.dimen.core_designsystem_padding_small)
            }
        )
    )
}


@Composable
private fun ErrorMessage(error: String) {
    Text(
        text = stringResource(R.string.feature_randomfact_error_prefix, error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun UpdateButton(
    onUpdateClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onUpdateClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(DesignR.dimen.core_designsystem_padding_default)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.feature_randomfact_button_corner_radius))
    ) {
        Text(stringResource(R.string.feature_randomfact_get_new_fact))
    }
}

@Composable
private fun LoadingOverlay(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(DesignR.dimen.core_designsystem_padding_default)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}