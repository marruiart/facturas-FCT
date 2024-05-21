package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.data.network.firebase.model.LoginResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authSvc: AuthService
) {

    val uid = authSvc.uid

    suspend operator fun invoke(email: String, password: String): LoginResult =
        authSvc.login(email, password)

}