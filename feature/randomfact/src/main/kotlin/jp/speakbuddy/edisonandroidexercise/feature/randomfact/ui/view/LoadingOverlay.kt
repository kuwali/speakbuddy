package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme

@Composable
internal fun LoadingOverlay(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.core_designsystem_padding_default)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingOverlayPreview() {
    EdisonAndroidExerciseTheme {
        LoadingOverlay(isVisible = true)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingOverlayHiddenPreview() {
    EdisonAndroidExerciseTheme {
        LoadingOverlay(isVisible = false)
    }
}
