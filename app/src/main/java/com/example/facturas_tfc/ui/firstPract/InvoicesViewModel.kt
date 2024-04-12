package com.example.facturas_tfc.ui.firstPract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facturas_tfc.data.repository.AppRepository
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import kotlinx.coroutines.launch


class InvoicesViewModel : ViewModel() {
    private val repository: AppRepository = AppRepository.getInstance()

    private val _invoices: MutableLiveData<List<InvoiceVO>> = MutableLiveData(emptyList())
    val invoices: LiveData<List<InvoiceVO>>
        get() = _invoices

    init {
        initObservers()
        viewModelScope.launch {
            repository.fetchInvoices()
        }
    }

    fun changeEnvironment(environment: String) {
        repository.setEnvironment(environment)
        viewModelScope.launch {
            repository.fetchInvoices()
        }
    }

    private fun initObservers() {
        viewModelScope.launch {
            repository.invoices.collect {invoices ->
                _invoices.postValue(invoices)
            }
        }
        viewModelScope.launch {
            repository.collectInvoicesFromNetwork()
        }
    }

}