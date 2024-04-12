package com.example.facturas_tfc.data.network.invoicesApi.models

import com.example.facturas_tfc.data.network.invoicesApi.models.InvoiceResponse

data class InvoicesListResponse(
    val facturas: List<InvoiceResponse>,
    val numFacturas: Int
) {
    fun getInvoicesList(): List<InvoiceResponse> {
        return facturas
    }
}