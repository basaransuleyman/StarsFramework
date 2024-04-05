package com.starFramework.feature.data.domainimpl.usecaseimpl

import com.starFramework.feature.data.persistance.datastore.StarDataStore
import com.starFramework.feature.domain.usecase.StarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideStarSaveUseCase(
        dataSource: StarDataStore
    ): StarUseCase {
        return StarUseCaseImpl(dataSource)
    }
}