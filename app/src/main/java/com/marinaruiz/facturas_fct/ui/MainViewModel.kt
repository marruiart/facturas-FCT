package com.marinaruiz.facturas_fct.ui

import androidx.lifecycle.ViewModel
import com.marinaruiz.facturas_fct.domain.LoginUseCase
import com.marinaruiz.facturas_fct.domain.LogoutUseCase

class MainViewModel : ViewModel() {

    private val logoutUseCase = LogoutUseCase()
    val uid = LoginUseCase().uid

    fun logout() = logoutUseCase()

}