package com.marinaruiz.facturas_fct.data.network.firebase

class FirebaseService(val client: FirebaseClient = FirebaseClient.getInstance()) {

    companion object {

        private var _INSTANCE: FirebaseService? = null

        fun getInstance(): FirebaseService {
            return _INSTANCE ?: FirebaseService()
        }
    }

    fun logout() {
        client.auth.signOut()
    }

}