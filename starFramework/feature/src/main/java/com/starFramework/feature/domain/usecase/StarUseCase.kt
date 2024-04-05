package com.starFramework.feature.domain.usecase

import com.starFramework.feature.domain.model.Star
import kotlinx.coroutines.flow.Flow

interface StarUseCase {
    suspend fun saveStars(stars: List<Star>)
    suspend fun loadStars(): List<Star>
}