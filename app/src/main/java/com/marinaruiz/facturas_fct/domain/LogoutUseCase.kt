package com.marinaruiz.facturas_fct.domain

import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.removePasswordInSharedPrefs
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(
    private val authSvc: AuthService
) {
    operator fun invoke() = run {
        authSvc.logout()
        removePasswordInSharedPrefs()
    }

}