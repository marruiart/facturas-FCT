package com.marinaruiz.facturas_fct.data.network.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.DynamicThemeActivity
import com.marinaruiz.facturas_fct.core.exceptions.ErrorOnSetDefaultAsyncException
import com.marinaruiz.facturas_fct.core.exceptions.RemoteConfigException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemoteConfigService private constructor(
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
) {
    private val _showInvoicesList: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val showInvoicesList: StateFlow<Boolean>
        get() = _showInvoicesList

    private val _theme: MutableStateFlow<Int> = MutableStateFlow(DynamicThemeActivity.DEFAULT_THEME)
    val theme: StateFlow<Int>
        get() = _theme

    private val _loading: MutableStateFlow<Boolean?> = MutableStateFlow(true)
    val loading: StateFlow<Boolean?>
        get() = _loading

    companion object {
        private const val TAG = "VIEWNEXT RemoteConfigService"

        private var _INSTANCE: RemoteConfigService? = null

        @Throws(RemoteConfigException::class)
        fun getInstance(): RemoteConfigService {
            _INSTANCE?.let {
                it._loading.value = null
                return it
            }
            return RemoteConfigService().also { rcSvc ->
                rcSvc.remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            rcSvc._showInvoicesList.value =
                                rcSvc.remoteConfig.getBoolean("showInvoicesList")
                            rcSvc._loading.value = false
                            _INSTANCE = rcSvc
                        } else {
                            throw ErrorOnSetDefaultAsyncException()
                        }
                    }
            }
        }
    }

    init {
        initRemoteConfigListener()
    }

    fun getStringValue(): String {
        return remoteConfig.getString(DynamicThemeActivity.KEY_THEME)
    }

    fun fetchAndActivate(): Task<Boolean> {
        return remoteConfig.fetchAndActivate()
    }

    private fun initRemoteConfigListener() =
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.updatedKeys);
                if (configUpdate.updatedKeys.contains("showInvoicesList")) {
                    remoteConfig.activate().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val newValue = remoteConfig.getBoolean("showInvoicesList")
                            _showInvoicesList.value = newValue
                            Log.d(TAG, "Remote config updated")
                        } else {
                            Log.w(TAG, "Error on config updating")
                        }
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

