package com.example.facturas_tfc.data.network

import android.util.Log
import com.example.facturas_tfc.data.network.invoicesApi.InvoicesApiService
import com.example.facturas_tfc.data.network.invoicesApi.models.InvoiceApiModel
import com.example.facturas_tfc.utils.ENVIRONMENT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkRepository private constructor(
    private val service: InvoicesApiService
) {
    private val _invoices: MutableStateFlow<List<InvoiceApiModel>?> = MutableStateFlow(null)
    val invoices: StateFlow<List<InvoiceApiModel>?>
        get() = _invoices

    companion object {
        private const val TAG = "VIEWNEXT NetworkRepository"

        private var _INSTANCE: NetworkRepository? = null

        fun getInstance(): NetworkRepository {
            return _INSTANCE ?: NetworkRepository(InvoicesApiService.getInstance())
        }
    }

    suspend fun fetchInvoices(environment: String = ENVIRONMENT) {
        try {
            val response = service.getAllInvoices(environment)
            if (response.isSuccessful && response.body() != null) {
                Log.d(TAG, response.body().toString())
                _invoices.value = response.body()!!.getInvoicesList().map { it.asApiModel() }
            } else {
                Log.e(TAG, response.message())
                _invoices.value = emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            _invoices.value = null
        }

    }
}