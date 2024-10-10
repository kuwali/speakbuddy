package jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import jp.speakbuddy.edisonandroidexercise.core.designsystem.R
import jp.speakbuddy.edisonandroidexercise.core.designsystem.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.flowOf

private const val ANIMATION_DURATION_MILLIS = 300

@Composable
internal fun FavoriteFactsList(
    favoriteFacts: LazyPagingItems<Fact>,
    animatingItems: Set<String>,
    onToggleFavorite: (Fact) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.core_designsystem_padding_default)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.core_designsystem_padding_default))
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

@Preview(showBackground = true)
@Composable
fun FavoriteFactsListPreview() {
    val sampleFacts = listOf(
        Fact(
            fact = "The platypus, a semiaquatic egg-laying mammal, doesn't have a stomach.",
            length = 69,
            isFavorite = true
        ),
        Fact(
            fact = "A group of flamingos is called a 'flamboyance'.",
            length = 48,
            isFavorite = true
        ),
        Fact(
            fact = "Honeybees can recognize human faces.",
            length = 37,
            isFavorite = true
        )
    )

    val fakePagingItems = flowOf(PagingData.from(sampleFacts)).collectAsLazyPagingItems()

    EdisonAndroidExerciseTheme {
        FavoriteFactsList(
            favoriteFacts = fakePagingItems,
            animatingItems = emptySet(),
            onToggleFavorite = {}
        )
    }
}