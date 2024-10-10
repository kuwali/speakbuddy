package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

private const val MIN_SHOW_CHAR_LENGTH = 100

@Composable
internal fun FactInfoCards(uiState: UiState) {
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
                    .padding(dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_small))
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
                is InfoCardType.DoubleFirst -> dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_zero)

                is InfoCardType.DoubleSecond -> dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_small)
            },
            end = when (cardType) {
                is InfoCardType.Single,
                is InfoCardType.DoubleSecond -> dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_zero)

                is InfoCardType.DoubleFirst -> dimensionResource(jp.speakbuddy.edisonandroidexercise.core.designsystem.R.dimen.core_designsystem_padding_small)
            }
        )
    )
}

@Preview(name = "Long Fact", showBackground = true)
@Composable
fun PreviewFactInfoCardsOnlyCharLength() {
    EdisonAndroidExerciseTheme {
        FactInfoCards(
            uiState = UiState.Success(
                fact = "This is a long fact about cat only with a lot of characters",
                factLength = 105,
                containsMultipleCats = false,
                isFavorite = false,
            )
        )
    }
}

@Preview(name = "Multiple Cats", showBackground = true)
@Composable
fun PreviewFactInfoCardsOnlyMultipleCats() {
    EdisonAndroidExerciseTheme {
        FactInfoCards(
            uiState = UiState.Success(
                fact = "This is a short fact about multiple cats",
                factLength = 30,
                containsMultipleCats = true,
                isFavorite = false,
            )
        )
    }
}

@Preview(name = "Long and Multiple Cats", showBackground = true)
@Composable
fun PreviewFactInfoCardsBoth() {
    EdisonAndroidExerciseTheme {
        FactInfoCards(
            uiState = UiState.Success(
                fact = "This is a long fact about multiple cats where the fact is longer than the fact about cats",
                factLength = 105,
                containsMultipleCats = true,
                isFavorite = false,
            )
        )
    }
}

@Preview(name = "Short Fact", showBackground = true)
@Composable
fun PreviewFactInfoCardsNeither() {
    EdisonAndroidExerciseTheme {
        FactInfoCards(
            uiState = UiState.Success(
                fact = "This is a short fact about cat",
                factLength = 30,
                containsMultipleCats = false,
                isFavorite = false,
            )
        )
    }
}