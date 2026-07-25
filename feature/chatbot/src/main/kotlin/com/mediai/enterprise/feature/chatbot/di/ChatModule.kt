package com.mediai.enterprise.feature.chatbot.di

import com.mediai.enterprise.feature.chatbot.data.remote.ChatApiService
import com.mediai.enterprise.feature.chatbot.data.repository.ChatRepositoryImpl
import com.mediai.enterprise.feature.chatbot.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    companion object {
        @Provides
        @Singleton
        fun provideChatApiService(retrofit: Retrofit): ChatApiService {
            return retrofit.create(ChatApiService.class.java)
        }
    }
}
