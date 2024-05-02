package com.marinaruiz.facturas_fct.ui.secondPract.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.data.repository.AppRepository
import com.marinaruiz.facturas_fct.data.repository.model.SmartSolarDetailVO
import kotlinx.coroutines.launch

class SmartSolarViewModel : ViewModel() {
    private val repository: AppRepository = AppRepository.getInstance()
    private val network = NetworkConnectionManager.getInstance(viewModelScope)

    private var _details = MutableLiveData<SmartSolarDetailVO>(null)
    val details: LiveData<SmartSolarDetailVO>
        get() = _details

    companion object {
        private const val TAG = "VIEWNEXT SmartSolarViewModel"
    }

    init {
        checkNetworkConnection()
        initObservers()
    }

    private fun initObservers() {
        viewModelScope.launch {
            repository.ssDetails.collect { details ->
                details?.let {

                    _details.postValue(it)
                }
            }
        }
        viewModelScope.launch {
            repository.collectSmartSolarDetailsFromNetwork()
        }
    }

    private fun checkNetworkConnection() {
        network.startListenNetworkState()
        viewModelScope.launch {
            network.isNetworkConnectedFlow.collect { isConnected ->
                Log.d(TAG, "Network state: ${if (isConnected) "connected" else "disconnected"}")
                if (isConnected) {
                    repository.fetchSmartSolarDetail()
                }
            }
        }
    }
}