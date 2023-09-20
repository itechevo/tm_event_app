package com.kupesan.tmapp.di

import com.kupesan.domain.EventInteractor
import com.kupesan.domain.repository.EventRepository
import com.kupesan.domain.usecase.EventUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Reusable
    @Provides
    fun provideRoundUpUseCase(
        @IODispatcher dispatcher: CoroutineDispatcher,
        eventRepository: EventRepository
    ): EventUseCase {
        return EventInteractor(eventRepository, dispatcher)
    }
}