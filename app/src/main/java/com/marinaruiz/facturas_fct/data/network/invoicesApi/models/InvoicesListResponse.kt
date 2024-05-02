package com.marinaruiz.facturas_fct.data.network.invoicesApi.models

data class InvoicesListResponse(
    val facturas: List<InvoiceResponse>,
    val numFacturas: Int
) {
    fun getInvoicesList(): List<InvoiceResponse> {
        return facturas
    }
}