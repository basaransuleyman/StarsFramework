package com.starFramework.feature.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DataStoreTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    private lateinit var starDataStore: StarDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(mockContext.applicationContext).thenReturn(mockContext)
        `when`(mockContext.getSharedPreferences("star_preferences", Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        starDataStore = StarDataStore(mockSharedPreferences)
    }

    @Test
    fun saveAndReturnStarsTest() {
        //given
        val stars = listOf(
            Star("S", "Red", "Bright"),
            Star("B", "Yellow", "Not so much")
        )
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(mockSharedPreferences.edit()).thenReturn(editor)
        val gson = Gson()
        val starsJson = gson.toJson(stars)

        //when
        `when`(editor.putString("stars", starsJson)).thenReturn(editor)
        `when`(mockSharedPreferences.getString("stars", null)).thenReturn(starsJson)

        //then
        val returnedStars = starDataStore.saveAndReturnStars(stars)
        assertEquals(stars, returnedStars)
    }
}