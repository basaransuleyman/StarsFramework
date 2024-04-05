package com.starFramework.feature.data.domainimpl.usecaseimpl

import com.starFramework.feature.core.IODispatcher
import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.model.Star
import com.starFramework.feature.domain.usecase.StarUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class StarUseCaseImpl @Inject constructor(
    private val dataStore: StarDataStore,
    @IODispatcher private val dispatcher: CoroutineContext
) : StarUseCase {

    override suspend fun saveStars(stars: List<Star>): Flow<List<Star>> = flow {
        val savedStars = dataStore.saveAndReturnStars(stars)
        emit(savedStars.map { it })
    }.flowOn(dispatcher)

    override fun loadStars(): List<Star> {
        return dataStore.loadStars()
    }
}