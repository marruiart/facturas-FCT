package com.marinaruiz.facturas_fct.data.network.firebase.model

sealed class LoginResult {
    data object Error : LoginResult()
    data object Success : LoginResult()
}