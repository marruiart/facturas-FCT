package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.removePasswordInSharedPrefs
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService


class LogoutUseCase(
    private val authSvc: AuthService = AuthService.getInstance()
) {
    operator fun invoke() = run {
        authSvc.logout()
        removePasswordInSharedPrefs()
    }

}