package jp.speakbuddy.edisonandroidexercise.core.domain.usecase

import jp.speakbuddy.edisonandroidexercise.core.domain.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ToggleFavoriteUseCaseTest {

    private val repository: FactRepository = mock()
    private lateinit var useCase: ToggleFavoriteUseCase

    @BeforeEach
    fun setup() {
        useCase = ToggleFavoriteUseCase(repository)
    }

    @Test
    fun `invoke calls toggleFavorite on repository with given fact`() = runTest {
        // Given
        val fact = Fact("Test fact", 42)

        // When
        useCase(fact)

        // Then
        verify(repository).toggleFavorite(fact)
    }
}
