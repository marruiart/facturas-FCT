package com.marinaruiz.facturas_fct.data.network.firebase

import android.util.Log

class FirebaseService private constructor(
    val auth: AuthService = AuthService.getInstance(),
    val remoteConfig: RemoteConfigService = RemoteConfigService.getInstance()
) {

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