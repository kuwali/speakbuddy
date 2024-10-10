package jp.speakbuddy.edisonandroidexercise.feature.fact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import jp.speakbuddy.edisonandroidexercise.feature.favoritefact.ui.FavoritesFactScreen
import jp.speakbuddy.edisonandroidexercise.feature.randomfact.ui.RandomFactScreen
import kotlinx.coroutines.launch

private const val PAGER_CONTENT_DESCRIPTION = "pager_content"

/**
 * Composable function that displays the main screen for the Fact feature.
 * It includes a top app bar, a tab row for navigation between Random Facts and Favorites,
 * and a horizontal pager to switch between the corresponding screens.
 */
@Composable
fun FactScreen() {
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { FactScreenTopAppBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                tabs.forEachIndexed { index, tabInfo ->
                    FactTab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        titleRes = tabInfo.titleRes
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .semantics { contentDescription = PAGER_CONTENT_DESCRIPTION }
            ) { page ->
                tabs[page].screen()
            }
        }
    }
}

@Composable
private fun FactTab(
    selected: Boolean,
    onClick: () -> Unit,
    titleRes: Int
) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = { Text(stringResource(titleRes)) },
    )
}

private data class TabInfo(val titleRes: Int, val screen: @Composable () -> Unit)

private val tabs = listOf(
    TabInfo(R.string.feature_fact_random_fact_title) { RandomFactScreen() },
    TabInfo(R.string.feature_fact_favorites_title) { FavoritesFactScreen() }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FactScreenTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.feature_fact_cat_facts_title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
