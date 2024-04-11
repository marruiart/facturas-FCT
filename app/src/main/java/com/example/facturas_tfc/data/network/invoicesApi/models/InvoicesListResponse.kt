package com.example.facturas.data.network.invoicesApi.models

data class InvoicesListResponse(
    val facturas: List<InvoiceResponse>,
    val numFacturas: Int
) {
    fun getInvoicesList(): List<InvoiceResponse> {
        return facturas
    }
}