package com.starFramework.feature.domain

import com.starFramework.feature.data.domainimpl.usecaseimpl.StarUseCaseImpl
import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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
        starUseCase = StarUseCaseImpl(
            mockDataStore
        )
    }

    @Test
    fun `saveStars emits correct data`() = runTest {
        val mockDataStore = mock(StarDataStore::class.java)
        val starUseCase = StarUseCaseImpl(mockDataStore)
        val stars = listOf(
            Star("S", "Red", "Bright"),
            Star("B", "Yellow", "Not so much")
        )

        // When
        starUseCase.saveStars(stars)

        // Then
        verify(mockDataStore).saveStars(stars)
    }
}