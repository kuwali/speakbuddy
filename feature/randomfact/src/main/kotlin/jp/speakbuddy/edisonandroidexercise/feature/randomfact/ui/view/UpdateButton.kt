package jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R as DesignR

@Composable
internal fun UpdateButton(
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

@Preview(showBackground = true)
@Composable
fun UpdateButtonPreview() {
    EdisonAndroidExerciseTheme {
        UpdateButton(
            onUpdateClick = { /* Preview click action */ },
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisabledUpdateButtonPreview() {
    EdisonAndroidExerciseTheme {
        UpdateButton(
            onUpdateClick = { /* Preview click action */ },
            enabled = false
        )
    }
}