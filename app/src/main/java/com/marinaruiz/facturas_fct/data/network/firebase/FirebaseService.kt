package com.marinaruiz.facturas_fct.data.network.firebase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    remoteConfig: RemoteConfigService
) {

    val showInvoicesList = remoteConfig.showInvoicesList

    companion object {
        private const val TAG = "VIEWNEXT FirebaseService"
    }

}