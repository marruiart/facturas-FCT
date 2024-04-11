package com.example.facturas_tfc.ui.firstPract

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facturas_tfc.data.repository.AppRepository
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import kotlinx.coroutines.launch


class InvoicesViewModel : ViewModel() {
    private val repository: AppRepository = AppRepository.getInstance()
    val invoices: LiveData<List<InvoiceVO>> = repository.invoices

    init {
        viewModelScope.launch {
            repository.refreshInvoicesList()
        }
    }

    fun changeEnvironment(environment: String) {
        repository.setEnvironment(environment)
    }

}