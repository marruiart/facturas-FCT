package com.marinaruiz.facturas_fct.data.network.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.marinaruiz.facturas_fct.R

class RemoteConfigService private constructor(
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
) {

    companion object {
        private const val TAG = "VIEWNEXT RemoteConfigService"

        private var _INSTANCE: RemoteConfigService? = null

        fun getInstance(): RemoteConfigService {
            _INSTANCE?.let {
                return it
            }
            return RemoteConfigService().also {
                it.remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                _INSTANCE = it
            }
        }
    }

    init {
        initRemoteConfigListener()
    }

    private fun initRemoteConfigListener() =
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.updatedKeys);

                if (configUpdate.updatedKeys.contains("showInvoicesList")) {
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d(TAG, "remote config updated")
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w(TAG, "Config update error with code: " + error.code, error)
            }
        })


    fun fetchAndActivateRemoteConfig() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Log.d(TAG, "Config params updated: $updated")
            } else {
                Log.d(TAG, "Error on config params.")
            }

        }
    }
}

