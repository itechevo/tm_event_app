package com.kupesan.tmapp.di

import com.kupesan.data.source.api.RetrofitProvider
import com.kupesan.data.source.api.TMApiService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Reusable
    @Provides
    fun provideTMApiService(): TMApiService {
        return RetrofitProvider.get(TMApiService.BASE_URL)
            .create(TMApiService::class.java)
    }
}