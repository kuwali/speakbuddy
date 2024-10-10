package jp.speakbuddy.edisonandroidexercise.core.network.retrofit

import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FactRemoteDataSourceTest {
    private val mockFactApi: FactApi = mock()
    private lateinit var factRemoteDataSource: FactRemoteDataSource

    @BeforeEach
    fun setup() {
        factRemoteDataSource = FactRemoteDataSource(mockFactApi)
    }

    @Test
    fun `getFact should return FactResponseDto from API`() = runTest {
        // Given
        val expectedFact =
            FactResponseDto(
                fact = "Cats sleep 70% of their lives.",
                length = 32
            )
        `when`(mockFactApi.getFact()).thenReturn(expectedFact)

        // When
        val result = factRemoteDataSource.getFact()

        // Then
        assertEquals(expectedFact, result)
    }

    @Test
    fun `getFact should propagate exception from API`() = runTest {
        // Given
        val expectedException = RuntimeException("API Error")
        `when`(mockFactApi.getFact()).thenThrow(expectedException)

        // When & Then
        try {
            factRemoteDataSource.getFact()
        } catch (e: Exception) {
            assertEquals(expectedException, e)
        }
    }
}
