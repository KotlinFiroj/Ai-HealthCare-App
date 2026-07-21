package com.mediai.enterprise.core.database.di

import android.content.Context
import androidx.room.Room
import com.mediai.enterprise.core.database.MediAIDatabase
import com.mediai.enterprise.core.database.dao.MedicineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MediAIDatabase {
        return Room.databaseBuilder(
            context,
            MediAIDatabase::class.java,
            "mediai_database"
        ).build()
    }

    @Provides
    fun provideMedicineDao(database: MediAIDatabase): MedicineDao {
        return database.medicineDao()
    }
}
