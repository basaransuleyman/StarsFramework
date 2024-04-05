package com.starFramework.feature.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DataStoreTest {
    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var starDataStore: StarDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor)
        starDataStore = StarDataStore(mockSharedPreferences)
    }

    @Test
    fun `saveStars calls putString and apply on SharedPreferences Editor`() {
        // Given
        val stars = listOf(Star("S", "Red", "Bright"), Star("B", "Yellow", "Not so much"))
        val starsJson = Gson().toJson(stars)

        // When
        starDataStore.saveStars(stars)

        // Then
        verify(mockEditor).putString("stars", starsJson)
        verify(mockEditor).apply()
    }

    @Test
    fun `loadStars returns correct list of stars from SharedPreferences`() {
        // Given
        val stars = listOf(Star("S", "Red", "Bright"), Star("B", "Yellow", "Not so much"))
        val starsJson = Gson().toJson(stars)
        `when`(mockSharedPreferences.getString("stars", null)).thenReturn(starsJson)

        // When
        val loadedStars = starDataStore.loadStars()

        // Then
        assertEquals(stars, loadedStars)
    }
}