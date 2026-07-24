package com.mediai.enterprise.feature.emergency.service

import android.content.Context
import android.telephony.SmsManager
import com.mediai.enterprise.core.database.dao.EmergencyDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SosService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationManager: LocationManager,
    private val emergencyDao: EmergencyDao
) {
    suspend fun triggerSos() {
        val location = locationManager.getCurrentLocation()
        val contacts = emergencyDao.getEmergencyContacts().first()

        val mapsLink = location?.let {
            "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
        } ?: "Location not available"

        val message = "EMERGENCY! I need help. My current location is: $mapsLink"

        val smsManager = context.getSystemService(SmsManager::class.java)
        contacts.forEach { contact ->
            try {
                smsManager.sendTextMessage(contact.phoneNumber, null, message, null, null)
            } catch (e: Exception) {
                // Handle failure
            }
        }
    }
}
