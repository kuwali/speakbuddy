package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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

@Preview(showBackground = true)
@Composable
fun ErrorStatePreview() {
    EdisonAndroidExerciseTheme {
        ErrorState(
            errorMessage = "An error occurred. Please try again.",
            onRetry = { /* Preview doesn't need actual functionality */ }
        )
    }
}