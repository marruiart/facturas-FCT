package com.marinaruiz.facturas_fct.domain

import android.content.Context
import android.widget.Toast
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.removePasswordInSharedPrefs
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.di.App


class ForgotPasswordUseCase(
    private val authSvc: AuthService = AuthService.getInstance()
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