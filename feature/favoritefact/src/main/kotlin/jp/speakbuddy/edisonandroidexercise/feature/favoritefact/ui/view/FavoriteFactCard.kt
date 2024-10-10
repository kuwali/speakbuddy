package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

private const val MAX_LINES_FACT_TEXT = 2

@Composable
internal fun FavoriteFactCard(
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

@Preview(showBackground = true)
@Composable
fun FavoriteFactCardPreview() {
    EdisonAndroidExerciseTheme {
        FavoriteFactCard(
            fact = Fact(
                fact = "The platypus, a semiaquatic egg-laying mammal, doesn't have a stomach. Instead, its esophagus connects directly to its intestines!",
                length = 134,
                isFavorite = true
            ),
            onUnfavorite = {}
        )
    }
}
