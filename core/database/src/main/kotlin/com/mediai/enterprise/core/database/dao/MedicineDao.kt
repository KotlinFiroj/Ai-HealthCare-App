package com.mediai.enterprise.core.database.dao

import androidx.room.*
import com.mediai.enterprise.core.database.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

/**
 * [MedicineDao]
 * Data Access Object for the medicines table.
 */
@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines ORDER BY startDate DESC")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: MedicineEntity): Long

    @Update
    suspend fun updateMedicine(medicine: MedicineEntity)

    @Delete
    suspend fun deleteMedicine(medicine: MedicineEntity)

    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getMedicineById(id: Long): MedicineEntity?
}
