package com.marinaruiz.facturas_fct.domain

import android.content.Context
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs.removePasswordInSharedPrefs
import com.marinaruiz.facturas_fct.data.network.firebase.AuthService
import com.marinaruiz.facturas_fct.di.App


class LogoutUseCase(
    private val authSvc: AuthService = AuthService.getInstance(),
    private val context: Context = App.context
) {
    operator fun invoke() = run {
        authSvc.logout()
        removePasswordInSharedPrefs()
    }

}