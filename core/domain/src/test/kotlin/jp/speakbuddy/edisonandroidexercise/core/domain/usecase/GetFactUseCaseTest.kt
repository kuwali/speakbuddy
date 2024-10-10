package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

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

class GetFactUseCaseTest {
    private val repository: FactRepository = mock()
    private lateinit var useCase: GetFactUseCase

    @BeforeEach
    fun setup() {
        useCase = GetFactUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns fact from repository when forceRefresh is false`() = runTest {
        // Given
        val expectedFact = Fact("Test fact", 21)
        `when`(repository.getFact(false)).thenReturn(flowOf(expectedFact))

        // When
        val result = useCase(false).single()

        // Then
        Assertions.assertEquals(expectedFact, result)
    }

    @Test
    fun `invoke returns fact from repository when forceRefresh is true`() = runTest {
        // Given
        val expectedFact = Fact("Refreshed fact", 21)
        `when`(repository.getFact(true)).thenReturn(flowOf(expectedFact))

        // When
        val result = useCase(true).single()

        // Then
        Assertions.assertEquals(expectedFact, result)
    }

    @Test
    fun `invoke uses default forceRefresh value when not provided`() = runTest {
        // Given
        val expectedFact = Fact("Default fact", 21)
        `when`(repository.getFact(false)).thenReturn(flowOf(expectedFact))

        // When
        val result = useCase().single()

        // Then
        Assertions.assertEquals(expectedFact, result)
    }
}
