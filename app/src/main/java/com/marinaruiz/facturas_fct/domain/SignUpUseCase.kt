package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.data.network.firebase.model.SignUpResult

class SignUpUseCase(private val authSvc: AuthService = AuthService.getInstance()) {

    suspend operator fun invoke(email: String, password: String): SignUpResult {
        return try {
            SignUpResult(null, authSvc.createAccount(email, password) != null)
        } catch (e: Exception) {
            SignUpResult(ErrorResponse("INVALID_SIGN_UP", e.message.toString()), false)
        }
    }
}