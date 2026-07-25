package com.mediai.enterprise.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [KeyStoreManager]
 * Manages secure generation and retrieval of keys from the Android Keystore.
 * Used for database encryption and PII protection.
 */
@Singleton
class KeyStoreManager @Inject constructor() {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    /**
     * Gets or generates a 256-bit AES key for database encryption.
     */
    fun getOrCreateDbKey(): ByteArray {
        val alias = "mediai_db_key"
        val existingKey = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry
        val key = existingKey?.secretKey ?: generateKey(alias)
        return key.encoded
    }

    private fun generateKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setKeySize(256)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }
}
