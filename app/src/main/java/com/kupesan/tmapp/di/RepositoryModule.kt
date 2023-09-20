package com.kupesan.tmapp.di

import com.kupesan.data.EventRepositoryImpl
import com.kupesan.data.source.api.TMApiService
import com.kupesan.data.source.local.AppDatabase
import com.kupesan.data.source.local.EventDao
import com.kupesan.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Reusable
    @Provides
    fun provideAccountRepository(
        @IODispatcher dispatcher: CoroutineDispatcher,
        tmApiService: TMApiService,
        eventDao: EventDao
    ): EventRepository {
        return EventRepositoryImpl(dispatcher, tmApiService, eventDao)
    }
}