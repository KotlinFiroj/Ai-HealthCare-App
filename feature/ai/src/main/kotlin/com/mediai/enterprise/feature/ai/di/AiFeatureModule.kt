package com.mediai.enterprise.feature.ai.di

import com.mediai.enterprise.feature.ai.data.repository.AiRepositoryImpl
import com.mediai.enterprise.feature.ai.domain.repository.AiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiFeatureModule {

    @Binds
    @Singleton
    abstract fun bindAiRepository(
        aiRepositoryImpl: AiRepositoryImpl
    ): AiRepository
}
