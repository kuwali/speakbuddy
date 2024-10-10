package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

import androidx.paging.PagingData
import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetFavoriteFactsUseCaseTest {

    private val repository: FactRepository = mock()
    private lateinit var useCase: GetFavoriteFactsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetFavoriteFactsUseCase(repository)
    }

    @Test
    fun `invoke returns paging data of favorite facts from repository`() = runTest {
        // Given
        val expectedFacts = PagingData.from(listOf(
            Fact("Favorite fact 1", 1),
            Fact("Favorite fact 2", 2)
        ))
        `when`(repository.getFavoriteFacts()).thenReturn(flowOf(expectedFacts))

        // When
        val result = useCase().single()

        // Then
        Assertions.assertEquals(expectedFacts, result)
    }

    @Test
    fun `invoke returns empty paging data when no favorite facts`() = runTest {
        // Given
        val emptyPagingData = PagingData.empty<Fact>()
        `when`(repository.getFavoriteFacts()).thenReturn(flowOf(emptyPagingData))

        // When
        val result = useCase().single()

        // Then
        Assertions.assertEquals(emptyPagingData, result)
    }
}
