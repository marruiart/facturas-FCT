package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.data.network.firebase.model.SignUpResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val authSvc: AuthService
) {

    suspend operator fun invoke(email: String, password: String): SignUpResult {
        return try {
            SignUpResult(null, authSvc.createAccount(email, password) != null)
        } catch (e: Exception) {
            SignUpResult(ErrorResponse("INVALID_SIGN_UP", e.message.toString()), false)
        }
    }
}