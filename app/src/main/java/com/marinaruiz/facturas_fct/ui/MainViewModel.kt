package com.marinaruiz.facturas_fct.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.data.network.firebase.FirebaseService
import com.marinaruiz.facturas_fct.data.repository.model.PracticeVO
import com.marinaruiz.facturas_fct.domain.LoginUseCase
import com.marinaruiz.facturas_fct.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebase: FirebaseService,
    private val logoutUseCase: LogoutUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val network = NetworkConnectionManager.getInstance(viewModelScope)

    private val _practicesWithInvoicesList = arrayListOf(
        PracticeVO(1, "Práctica 1"), PracticeVO(2, "Práctica 2"), PracticeVO(3, "Navegación")
    )
    private val _practicesWithoutInvoicesList = arrayListOf(
        PracticeVO(2, "Práctica 2"), PracticeVO(3, "Navegación")
    )
    val uid = loginUseCase.uid
    private val _practices = MutableLiveData(_practicesWithInvoicesList)
    val practices: LiveData<ArrayList<PracticeVO>>
        get() = _practices

    companion object {
        private const val TAG = "VIEWNEXT MainViewModel"
    }

    init {
        checkNetworkConnection()
    }

    private fun initPracticesWithRemoteConfig() {
        viewModelScope.launch {
            firebase?.let { firebaseSvc ->
                firebaseSvc.showInvoicesList.collect { show ->
                    _practices.value =
                        if (show) _practicesWithInvoicesList else _practicesWithoutInvoicesList
                    Log.d(TAG, "showInvoicesList: $show")
                }
            }
        }
    }

    private fun checkNetworkConnection() {
        viewModelScope.launch {
            network.isNetworkConnectedFlow.collect { isConnected ->
                Log.d(TAG, "Network state: ${if (isConnected) "connected" else "disconnected"}")
                if (isConnected) {
                    initPracticesWithRemoteConfig()
                }
            }
        }
    }

    fun logout() = logoutUseCase()

}