package com.marinaruiz.facturas_fct.data.network.firebase

import com.google.firebase.auth.FirebaseAuth

class FirebaseClient(val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    companion object {

        private var _INSTANCE: FirebaseClient? = null

        fun getInstance(): FirebaseClient {
            return _INSTANCE ?: FirebaseClient()
        }
    }

}