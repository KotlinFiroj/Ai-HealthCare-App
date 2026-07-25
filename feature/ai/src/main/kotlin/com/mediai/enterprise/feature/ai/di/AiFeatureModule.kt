package com.mediai.enterprise.feature.ai.di

import com.mediai.enterprise.feature.ai.data.remote.AiApiService
import com.mediai.enterprise.feature.ai.data.repository.AiRepositoryImpl
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiFeatureModule {

    @Binds
    @Singleton
    abstract fun bindAiRepository(
        aiRepositoryImpl: AiRepositoryImpl
    ): AiRepository

    companion object {
        @Provides
        @Singleton
        fun provideAiApiService(retrofit: Retrofit): AiApiService {
            return retrofit.create(AiApiService::class.java)
        }
    }
}
