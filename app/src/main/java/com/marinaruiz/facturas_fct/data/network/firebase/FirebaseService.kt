package com.marinaruiz.facturas_fct.data.network.firebase

import android.util.Log

class FirebaseService private constructor(
    auth: AuthService = AuthService.getInstance(),
    remoteConfig: RemoteConfigService = RemoteConfigService.getInstance()
) {

    val showInvoicesList = remoteConfig.showInvoicesList

    companion object {

        private const val TAG = "VIEWNEXT FirebaseService"

        private var _INSTANCE: FirebaseService? = null

        fun getInstance(): FirebaseService {
            return _INSTANCE ?: FirebaseService().also {
                Log.d(TAG, "Creating FirebaseService")
                _INSTANCE = it
            }
        }
    }

}