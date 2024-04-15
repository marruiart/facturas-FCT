package com.example.facturas_tfc.ui.firstPract

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facturas_tfc.core.NetworkConnectionManager
import com.example.facturas_tfc.data.repository.AppRepository
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import kotlinx.coroutines.launch


class InvoicesViewModel : ViewModel() {
    private val repository: AppRepository = AppRepository.getInstance()
    private val network = NetworkConnectionManager.getInstance(viewModelScope)

    private val _invoices: MutableLiveData<List<InvoiceVO>> = MutableLiveData(emptyList())
    val invoices: LiveData<List<InvoiceVO>>
        get() = _invoices

    companion object {
        private const val TAG = "VIEWNEXT InvoicesViewModel"
    }

    init {
        checkNetworkConnection()
        initObservers()
    }

    fun changeEnvironment(environment: String) {
        repository.setEnvironment(environment)
        viewModelScope.launch {
            if (network.isNetworkConnected) {
                repository.fetchInvoices()
            }
        }
    }

    private fun checkNetworkConnection() {
        network.startListenNetworkState()
        viewModelScope.launch {
            network.isNetworkConnectedFlow.collect { isConnected ->
                Log.d(TAG, "Network state: ${if (isConnected) "connected" else "disconnected"}")
                if (isConnected) {
                    repository.fetchInvoices()
                }
            }
        }
    }

    private fun initObservers() {
        viewModelScope.launch {
            repository.invoices.collect { invoices ->
                _invoices.postValue(invoices)
            }
        }
        viewModelScope.launch {
            repository.collectInvoicesFromNetwork()
        }
    }

}