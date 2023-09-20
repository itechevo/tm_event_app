package com.kupesan.tmapp

import com.kupesan.domain.model.EventModel
import com.kupesan.domain.model.NetworkResult
import com.kupesan.domain.model.Success
import com.kupesan.domain.repository.EventRepository
import com.kupesan.domain.usecase.EventUseCase
import com.kupesan.tmapp.di.UseCaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
@Module
object FakeUseCaseModule {

    var results: List<EventModel> = emptyList()

    @Singleton
    @Provides
    fun provideFakeEventUseCase() = object : EventUseCase {
        @ExperimentalCoroutinesApi
        override suspend fun getEventsByCountryCode(countryCode: String): Flow<NetworkResult<List<EventModel>?>> {
            return flowOf(Success(data = results))
        }
    }
}