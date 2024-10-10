package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

@Composable
internal fun EmptyFavoritesList() {
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

@Preview(showBackground = true)
@Composable
fun EmptyFavoritesListPreview() {
    EdisonAndroidExerciseTheme {
        EmptyFavoritesList()
    }
}