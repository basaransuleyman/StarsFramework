package com.starFramework.feature.domain

import com.starFramework.feature.data.domainimpl.usecaseimpl.StarUseCaseImpl
import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class StarUseCaseImplTest {
    @Mock
    private lateinit var mockDataStore: StarDataStore

    private lateinit var starUseCase: StarUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockDataStore = mock()
        starUseCase = com.starFramework.feature.data.domainimpl.usecaseimpl.StarUseCaseImpl(
            mockDataStore,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `saveStars emits correct data`() = runTest {
        // Given
        val stars = listOf(
            Star("S", "Red", "Bright"),
            Star("B", "Yellow", "Not so much")
        )
        val savedStars = listOf(
            Star("S", "Red", "Bright"),
            Star("B", "Yellow", "Not so much")
        )

        val flowOfSavedStars: List<Star> = savedStars
        `when`(mockDataStore.saveAndReturnStars(stars)).thenReturn(flowOfSavedStars)

        // when
        val result = mutableListOf<List<Star>>()
        starUseCase.saveStars(stars).collect {
            result.add(it)
        }

        // then
        assertEquals(listOf(savedStars), result)
        verify(mockDataStore).saveAndReturnStars(stars)
    }
}