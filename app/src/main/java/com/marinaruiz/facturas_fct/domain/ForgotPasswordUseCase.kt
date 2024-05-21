package com.marinaruiz.facturas_fct.domain

import android.content.Context
import android.widget.Toast
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForgotPasswordUseCase @Inject constructor(
    private val authSvc: AuthService
) {
    operator fun invoke(context: Context, email: String) = run {
        authSvc.resetPassword(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Error al enviar el email", Toast.LENGTH_SHORT).show()
            }
        }
    }

}