package com.starFramework.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starFramework.api.StarFrameworkInterface
import com.starFramework.feature.domain.model.Star
import com.starFramework.feature.domain.usecase.StarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarViewModel @Inject constructor(private val starUseCase: StarUseCase) :
    ViewModel(), StarFrameworkInterface {

    private val _stars = MutableStateFlow<List<Star>>(emptyList())
    val stars: StateFlow<List<Star>> = _stars

    private val _isSkyFull = MutableStateFlow(false)
    val isSkyFull = _isSkyFull.asStateFlow()

    init {
        viewModelScope.launch {
            val loadedStars = starUseCase.loadStars()
            _stars.value = loadedStars
        }
    }

    fun addStar(star: Star) {
        val updatedStars = _stars.value.toMutableList()
        if (updatedStars.size < 10) {
            updatedStars.add(star)
            _stars.value = updatedStars
            saveStars()
        } else {
            _isSkyFull.value = true
        }
        printStars()
    }

    fun resetSky() {
        _stars.value = emptyList()
        _isSkyFull.value = false
        saveStars()
        printStars()
    }

    private fun printStars() {
        println("Current Stars: ${_stars.value}")
        val brightStarsCount = _stars.value.count { it.brightness == "Bright" }
        println("Number of 'Bright' stars: $brightStarsCount")
    }

    fun saveStars() {
        viewModelScope.launch {
            starUseCase.saveStars(_stars.value)
        }
    }

    override fun addStarWithInterface(size: String, color: String, brightness: String) {
        val star = Star(size, color, brightness)
        addStar(star)
    }
}