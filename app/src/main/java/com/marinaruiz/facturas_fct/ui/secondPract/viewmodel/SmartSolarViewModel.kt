package com.marinaruiz.facturas_fct.ui.secondPract.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.data.repository.AppRepository
import com.marinaruiz.facturas_fct.data.repository.model.SmartSolarDetailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmartSolarViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
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