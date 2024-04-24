package com.example.facturas_tfc.data.repository

import com.example.facturas_tfc.core.utils.ENVIRONMENT
import com.example.facturas_tfc.data.local.InvoicesDatabase
import com.example.facturas_tfc.data.local.LocalDbRepository
import com.example.facturas_tfc.data.local.models.InvoiceEntity
import com.example.facturas_tfc.data.local.models.SSDetailsEntity
import com.example.facturas_tfc.data.local.models.asInvoiceVOList
import com.example.facturas_tfc.data.network.NetworkRepository
import com.example.facturas_tfc.data.network.invoicesApi.models.asInvoiceEntityList
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import com.example.facturas_tfc.data.repository.model.SmartSolarDetailVO
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppRepository private constructor(
    private val networkRepository: NetworkRepository,
    private val localDbRepository: LocalDbRepository
) {
    private var _environment: String = ENVIRONMENT

    val invoices: Flow<List<InvoiceVO>>
        get() {
            return localDbRepository.getAllInvoices.map {
                it.asInvoiceVOList()
            }
        }
    val ssDetails: Flow<SmartSolarDetailVO?>
        get() {
            return localDbRepository.getSmartSolarDetails.map { it?.asSmartSolarDetailVO() }
        }

    companion object {
        private var _INSTANCE: AppRepository? = null

        fun getInstance(): AppRepository {
            return _INSTANCE ?: AppRepository(
                NetworkRepository.getInstance(),
                LocalDbRepository(
                    InvoicesDatabase.getInstance().invoicesDao(),
                    InvoicesDatabase.getInstance().smartSolarDao()
                )
            )
        }
    }

    fun setEnvironment(environment: String) {
        _environment = environment
    }

    suspend fun collectInvoicesFromNetwork() {
        withContext(IO) {
            networkRepository.invoices.collect { invoicesOrNull ->
                invoicesOrNull?.let { invoices ->
                    refreshLocalInvoicesDb(invoices.asInvoiceEntityList())
                }
            }
        }
    }

    suspend fun collectSmartSolarDetailsFromNetwork() {
        withContext(IO) {
            networkRepository.details.collect { detailsOrNull ->
                refreshLocalSmartSolarDb(detailsOrNull?.asSmartSolarDetailsEntity())
            }
        }
    }

    suspend fun fetchInvoices() = networkRepository.fetchInvoices(_environment)

    suspend fun fetchSmartSolarDetail() = networkRepository.fetchSmartSolarDetails(_environment)

    private suspend fun refreshLocalInvoicesDb(invoices: List<InvoiceEntity>) {
        localDbRepository.deleteAllInvoices()
        localDbRepository.insertInvoices(invoices)
    }

    private suspend fun refreshLocalSmartSolarDb(details: SSDetailsEntity?) {
        localDbRepository.deleteDetails()
        details?.let {
            localDbRepository.insertDetails(it)
        }
    }

}