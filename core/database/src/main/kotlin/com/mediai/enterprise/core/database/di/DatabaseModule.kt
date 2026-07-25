package com.mediai.enterprise.core.database.di

import android.content.Context
import androidx.room.Room
import com.mediai.enterprise.core.database.MediAIDatabase
import com.mediai.enterprise.core.database.dao.ChatDao
import com.mediai.enterprise.core.database.dao.EmergencyDao
import com.mediai.enterprise.core.database.dao.HealthDao
import com.mediai.enterprise.core.database.dao.MedicineDao
import com.mediai.enterprise.core.security.KeyStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        keyStoreManager: KeyStoreManager
    ): MediAIDatabase {
        val passphrase = keyStoreManager.getOrCreateDbKey()
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            MediAIDatabase::class.java,
            "mediai_database"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMedicineDao(database: MediAIDatabase): MedicineDao {
        return database.medicineDao()
    }

    @Provides
    fun provideEmergencyDao(database: MediAIDatabase): EmergencyDao {
        return database.emergencyDao()
    }

    @Provides
    fun provideHealthDao(database: MediAIDatabase): HealthDao {
        return database.healthDao()
    }

    @Provides
    fun provideChatDao(database: MediAIDatabase): ChatDao {
        return database.chatDao()
    }
}
