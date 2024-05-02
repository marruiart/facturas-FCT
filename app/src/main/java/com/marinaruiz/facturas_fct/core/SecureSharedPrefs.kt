package com.marinaruiz.facturas_fct.core

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.marinaruiz.facturas_fct.di.App


object SecureSharedPrefs {
    private const val PREFS_FILE_NAME = "secure_prefs"
    private var sharedPreferences: SharedPreferences? = null

    private fun getSecSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return sharedPreferences ?: EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveInSecSharedPrefs(key: String, data: String?) {
        val sharedPreferences = getSecSharedPreferences(App.context)
        val editor = sharedPreferences.edit()
        editor.putString(key, data)
        editor.apply()
    }

    fun retrieveFromSecSharedPrefs(key: String): String? {
        val sharedPreferences = getSecSharedPreferences(App.context)
        return sharedPreferences.getString(key, null)
    }

    fun removePasswordInSharedPrefs() = saveInSecSharedPrefs("pass", null)
}
