package com.mediai.enterprise.feature.reports.domain.model

import java.time.LocalDateTime

data class MedicalReport(
    val id: String,
    val title: String,
    val category: ReportCategory,
    val date: LocalDateTime,
    val fileUrl: String,
    val summary: String? = null,
    val syncStatus: SyncStatus = SyncStatus.LOCAL
)

enum class ReportCategory(val displayName: String) {
    BLOOD_TEST("Blood Test"),
    MRI("MRI Scan"),
    CT_SCAN("CT Scan"),
    ECG("ECG"),
    X_RAY("X-Ray"),
    PRESCRIPTION("Prescription"),
    OTHERS("Others")
}

enum class SyncStatus {
    LOCAL, SYNCING, SYNCED, ERROR
}

data class PrescriptionData(
    val doctorName: String?,
    val hospitalName: String?,
    val date: String?,
    val medicines: List<MedicineInfo>
)

data class MedicineInfo(
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String? = null,
    val notes: String? = null
)
