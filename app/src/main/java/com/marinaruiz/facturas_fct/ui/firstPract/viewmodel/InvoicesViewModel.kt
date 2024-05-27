package com.marinaruiz.facturas_fct.ui.firstPract.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.NetworkConnectionManager
import com.marinaruiz.facturas_fct.data.repository.AppRepository
import com.marinaruiz.facturas_fct.data.repository.FilterService
import com.marinaruiz.facturas_fct.data.repository.model.Filter
import com.marinaruiz.facturas_fct.data.repository.model.InvoiceVO
import com.marinaruiz.facturas_fct.data.repository.model.applyFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.floor

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val network = NetworkConnectionManager.getInstance(viewModelScope)

    private var _invoices: List<InvoiceVO> = emptyList()
    private var _filteredInvoices = MutableLiveData(_invoices)
    val invoices: LiveData<List<InvoiceVO>>
        get() = _filteredInvoices

    private var _filter = FilterService.filter
    val filter: Filter
        get() = _filter.copy()

    companion object {
        private const val TAG = "VIEWNEXT InvoicesViewModel"
    }

    init {
        checkNetworkConnection()
        initObservers()
    }

    private fun checkNetworkConnection() {
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
                setFilterAmountRange(invoices)
                _invoices = invoices
                _filteredInvoices.postValue(invoices.applyFilter(_filter))
            }
        }
        viewModelScope.launch {
            repository.collectInvoicesFromNetwork()
        }
    }

    private fun setFilterAmountRange(list: List<InvoiceVO>) {
        var max: Float = Float.MIN_VALUE
        var min: Float = Float.MAX_VALUE
        list.forEach { invoice ->
            val amountCeil = ceil(invoice.amount)
            val amountFloor = floor(invoice.amount)
            max = if (amountCeil > max) amountCeil else max
            min = if (amountFloor < min) amountFloor else min
        }
        _filter.setAmountRange(min, max)
        if (_filter.selectedAmount.max != null && _filter.selectedAmount.max!! > max) {
            _filter.selectedAmount.max = null
        }
        if (_filter.selectedAmount.min != null && _filter.selectedAmount.min!! < min) {
            _filter.selectedAmount.min = null
        }
    }


    fun applyFilter(filter: Filter) {
        _filter = filter
        _filteredInvoices.value = _invoices.applyFilter(_filter)
    }

    fun changeEnvironment(environment: String) {
        repository.setEnvironment(environment)
        viewModelScope.launch {
            if (network.isNetworkConnected) {
                repository.fetchInvoices()
            }
        }
    }
}