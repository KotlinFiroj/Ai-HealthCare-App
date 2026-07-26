package com.mediai.enterprise.core.security

import okhttp3.CertificatePinner

/**
 * [SslPinning]
 * Provides the OkHttp [CertificatePinner] configuration.
 * Hardens network security against Man-in-the-Middle (MitM) attacks.
 */
object SslPinning {
    private const val HOSTNAME = "api.mediai-healthcare.com"

    // Example SHA-256 hashes of the server's public key certificate.
    // In production, these must match your actual SSL certificate.
    private const val PIN_1 = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
    private const val PIN_2 = "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB="

    val certificatePinner: CertificatePinner = CertificatePinner.Builder()
        .add(HOSTNAME, PIN_1)
        .add(HOSTNAME, PIN_2)
        .build()
}
