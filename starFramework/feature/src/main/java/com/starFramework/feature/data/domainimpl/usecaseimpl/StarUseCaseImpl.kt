package com.starFramework.feature.data.domainimpl.usecaseimpl

import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import com.starFramework.feature.domain.usecase.StarUseCase
import javax.inject.Inject

class StarUseCaseImpl @Inject constructor(
    private val dataStore: StarDataStore
) : StarUseCase {

    override suspend fun saveStars(stars: List<Star>) {
        dataStore.saveStars(stars)
    }

    override suspend fun loadStars(): List<Star> {
        return dataStore.loadStars()
    }
}