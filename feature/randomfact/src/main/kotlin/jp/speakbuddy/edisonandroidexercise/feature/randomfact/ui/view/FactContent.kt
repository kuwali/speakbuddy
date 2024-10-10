package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.R
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.UiState

@Composable
internal fun FactContent(uiState: UiState, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.feature_randomfact_card_corner_radius)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.feature_randomfact_card_elevation))
    ) {
        Box(
            modifier = Modifier.padding(dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_default)),
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

                        Spacer(modifier = Modifier.height(dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_small)))

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
private fun ErrorMessage(error: String) {
    Text(
        text = stringResource(R.string.feature_randomfact_error_prefix, error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(name = "Success State - Not Favorite", showBackground = true)
@Composable
fun FactContentSuccessNotFavoritePreview() {
    EdisonAndroidExerciseTheme {
        FactContent(
            uiState = UiState.Success(
                fact = "The shortest war in history lasted 38 minutes.",
                factLength = 43,
                isFavorite = false,
                containsMultipleCats = false,
            ),
            onToggleFavorite = {}
        )
    }
}

@Preview(name = "Success State - Favorite", showBackground = true)
@Composable
fun FactContentSuccessFavoritePreview() {
    EdisonAndroidExerciseTheme {
        FactContent(
            uiState = UiState.Success(
                fact = "Honey never spoils. Archaeologists have found pots of honey in ancient Egyptian tombs that are over 3,000 years old and still perfectly edible.",
                factLength = 43,
                isFavorite = true,
                containsMultipleCats = false,
            ),
            onToggleFavorite = {}
        )
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
fun FactContentErrorPreview() {
    EdisonAndroidExerciseTheme {
        FactContent(
            uiState = UiState.Error("An error occurred while fetching the fact."),
            onToggleFavorite = {}
        )
    }
}

@Preview(name = "Loading Fact", showBackground = true)
@Composable
fun FactContentLoadingFactPreview() {
    EdisonAndroidExerciseTheme {
        FactContent(
            uiState = UiState.Loading,
            onToggleFavorite = {}
        )
    }
}