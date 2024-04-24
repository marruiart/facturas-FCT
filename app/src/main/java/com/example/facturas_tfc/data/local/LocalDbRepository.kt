package com.example.facturas_tfc.data.local

import androidx.annotation.WorkerThread
import com.example.facturas_tfc.data.local.models.InvoiceEntity
import com.example.facturas_tfc.data.local.models.SSDetailsEntity
import kotlinx.coroutines.flow.Flow

class LocalDbRepository(
    private val invoiceDao: InvoiceDao,
    private val smartSolarDao: SmartSolarDao
) {

    val getAllInvoices: Flow<List<InvoiceEntity>> = invoiceDao.getAllInvoices()

    @WorkerThread
    suspend fun insertInvoices(invoices: List<InvoiceEntity>) {
        invoiceDao.saveInvoices(invoices)
    }

    @WorkerThread
    suspend fun deleteAllInvoices() {
        invoiceDao.deleteAll()
    }

    val getSmartSolarDetails: Flow<SSDetailsEntity?> = smartSolarDao.getSmartSolarDetails()

    @WorkerThread
    suspend fun insertDetails(details: SSDetailsEntity) {
        smartSolarDao.saveSmartSolarDetails(details)
    }

    @WorkerThread
    suspend fun deleteDetails() {
        smartSolarDao.deleteAll()
    }
}