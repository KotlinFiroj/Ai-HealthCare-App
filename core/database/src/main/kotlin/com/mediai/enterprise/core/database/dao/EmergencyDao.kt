package com.mediai.enterprise.core.database.dao

import androidx.room.*
import com.mediai.enterprise.core.database.entity.EmergencyContactEntity
import com.mediai.enterprise.core.database.entity.MedicalProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {
    @Query("SELECT * FROM emergency_contacts")
    fun getEmergencyContacts(): Flow<List<EmergencyContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: EmergencyContactEntity)

    @Delete
    suspend fun deleteContact(contact: EmergencyContactEntity)

    @Query("SELECT * FROM medical_profile WHERE userId = :userId")
    fun getMedicalProfile(userId: String): Flow<MedicalProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMedicalProfile(profile: MedicalProfileEntity)
}
