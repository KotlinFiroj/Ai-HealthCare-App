package com.mediai.enterprise.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mediai.enterprise.core.database.MediAIDatabase
import com.mediai.enterprise.core.database.entity.MedicineEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MedicineDaoTest {

    private lateinit var database: MediAIDatabase
    private lateinit var medicineDao: MedicineDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MediAIDatabase::class.java).build()
        medicineDao = database.medicineDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetAllMedicines() = runBlocking {
        val medicine = MedicineEntity(
            name = "Aspirin",
            dosage = "100mg",
            frequency = "Daily",
            reminderTimes = "08:00",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + 86400000
        )
        medicineDao.insertMedicine(medicine)

        val allMedicines = medicineDao.getAllMedicines().first()
        assertEquals(1, allMedicines.size)
        assertEquals("Aspirin", allMedicines[0].name)
    }

    @Test
    fun deleteMedicine() = runBlocking {
        val medicine = MedicineEntity(
            name = "Aspirin",
            dosage = "100mg",
            frequency = "Daily",
            reminderTimes = "08:00",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + 86400000
        )
        val id = medicineDao.insertMedicine(medicine)
        val inserted = medicineDao.getMedicineById(id)
        assertNotNull(inserted)

        medicineDao.deleteMedicine(inserted!!)
        val allMedicines = medicineDao.getAllMedicines().first()
        assertTrue(allMedicines.isEmpty())
    }

    private fun assertNotNull(obj: Any?) {
        assertTrue(obj != null)
    }
}
