package jp.speakbuddy.edisonandroidexercise.core.data.repository

import jp.speakbuddy.edisonandroidexercise.core.data.model.toEntity
import jp.speakbuddy.edisonandroidexercise.core.data.model.toModel
import jp.speakbuddy.edisonandroidexercise.core.data.paging.FavoriteFactsPagingSource
import jp.speakbuddy.edisonandroidexercise.core.database.dao.FactDao
import jp.speakbuddy.edisonandroidexercise.core.database.model.FactEntity
import jp.speakbuddy.edisonandroidexercise.core.model.Fact
import jp.speakbuddy.edisonandroidexercise.core.network.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.core.network.model.FactResponseDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class FactRepositoryImplTest {

    private lateinit var factRepositoryImpl: FactRepositoryImpl
    private val favoriteFactsPagingSource: FavoriteFactsPagingSource = mock()
    private val networkDataSource: FactNetworkDataSource = mock()
    private val dao: FactDao = mock()

    @BeforeEach
    fun setup() {
        factRepositoryImpl = FactRepositoryImpl(favoriteFactsPagingSource, networkDataSource, dao)
    }

    @Test
    fun `getFact with forceRefresh true should fetch from network and update local`() = runTest {
        // Given
        val remoteFact = FactResponseDto("Remote fact", 21)
        `when`(networkDataSource.getFact()).thenReturn(remoteFact)
        `when`(dao.insertFact(remoteFact.toEntity())).thenReturn(Unit)

        // When
        val result = factRepositoryImpl.getFact(forceRefresh = true).first()

        // Then
        Assertions.assertEquals(remoteFact.toModel(), result)
        verify(networkDataSource).getFact()
        verify(dao).insertFact(remoteFact.toEntity())
    }

    @Test
    fun `getFact with forceRefresh false and local data available should return local data`() =
        runTest {
            // Given
            val localFact = FactEntity(id = 1, fact = "Local fact", length = 21)
            `when`(dao.getLatestFact()).thenReturn(flowOf(localFact))

            // When
            val result = factRepositoryImpl.getFact(forceRefresh = false).first()

            // Then
            Assertions.assertEquals(localFact.toModel(), result)
            verify(networkDataSource, never()).getFact()
        }

    @Test
    fun `getFact with forceRefresh false and no local data should fetch from network`() = runTest {
        // Given
        val remoteFact = FactResponseDto("Remote fact", 21)
        `when`(dao.getLatestFact()).thenReturn(flowOf(null))
        `when`(networkDataSource.getFact()).thenReturn(remoteFact)
        `when`(dao.insertFact(remoteFact.toEntity())).thenReturn(Unit)

        // When
        val result = factRepositoryImpl.getFact(forceRefresh = false).first()

        // Then
        Assertions.assertEquals(remoteFact.toModel(), result)
        verify(networkDataSource).getFact()
        verify(dao).insertFact(remoteFact.toEntity())
    }

    @Test
    fun `toggleFavorite should update favorite status in local database`() = runTest {
        // Given
        val fact = Fact("Test fact", 21, false)
        `when`(dao.updateFavoriteStatus(fact.fact, true)).thenReturn(Unit)

        // When
        factRepositoryImpl.toggleFavorite(fact)

        // Then
        verify(dao).updateFavoriteStatus(fact.fact, true)
    }
}
