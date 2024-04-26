package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.data.network.firebase.model.LoginResult

class LoginUseCase(private val authSvc: AuthService = AuthService.getInstance()) {

    val uid = authSvc.uid

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authSvc.login(email, password)

}