package com.mediai.enterprise.feature.appointment.di

import com.mediai.enterprise.feature.appointment.data.remote.AppointmentApiService
import com.mediai.enterprise.feature.appointment.data.repository.AppointmentRepositoryImpl
import com.mediai.enterprise.feature.appointment.domain.repository.AppointmentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppointmentModule {

    @Binds
    @Singleton
    abstract fun bindAppointmentRepository(
        appointmentRepositoryImpl: AppointmentRepositoryImpl
    ): AppointmentRepository

    companion object {
        @Provides
        @Singleton
        fun provideAppointmentApiService(retrofit: Retrofit): AppointmentApiService {
            return retrofit.create(AppointmentApiService::class.java)
        }
    }
}
