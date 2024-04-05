package com.starFramework.feature.data.persistance.datastore

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.starFramework.feature.domain.model.Star
import javax.inject.Inject

class StarDataStore @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveAndReturnStars(stars: List<Star>): List<Star> {
        saveStars(stars)
        return loadStars()
    }

    private fun saveStars(stars: List<Star>) {
        sharedPreferences.edit().apply {
            val starsJson = Gson().toJson(stars)
            putString("stars", starsJson)
            apply()
        }
    }

    fun loadStars(): List<Star> {
        val starsJson = sharedPreferences.getString("stars", null)
        return if (starsJson != null) {
            val type = object : TypeToken<List<Star>>() {}.type
            Gson().fromJson(starsJson, type)
        } else {
            emptyList()
        }
    }
}
