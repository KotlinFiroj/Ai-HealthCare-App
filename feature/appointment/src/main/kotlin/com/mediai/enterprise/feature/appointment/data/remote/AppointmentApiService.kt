package com.mediai.enterprise.feature.appointment.data.remote

import com.mediai.enterprise.feature.appointment.data.remote.model.AppointmentRequestDto
import com.mediai.enterprise.feature.appointment.data.remote.model.AppointmentResponseDto
import com.mediai.enterprise.feature.appointment.data.remote.model.DoctorDto
import retrofit2.http.*

interface AppointmentApiService {
    @GET("doctors/")
    suspend fun searchDoctors(
        @Query("query") query: String? = null,
        @Query("specialization") specialization: String? = null
    ): List<DoctorDto>

    @GET("doctors/{id}")
    suspend fun getDoctor(@Path("id") id: String): DoctorDto

    @POST("appointments/")
    suspend fun bookAppointment(@Body request: AppointmentRequestDto): AppointmentResponseDto

    @GET("appointments/")
    suspend fun getAppointments(): List<AppointmentResponseDto>
}
