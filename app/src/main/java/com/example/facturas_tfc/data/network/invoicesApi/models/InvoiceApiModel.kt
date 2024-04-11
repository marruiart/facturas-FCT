package com.example.facturas.data.network.invoicesApi.models

import com.example.facturas_tfc.data.repository.model.InvoiceVO
import java.time.LocalDate

data class InvoiceApiModel(
    val stateResource: Int,
    val date: LocalDate,
    val amount: Float
) {
    fun asInvoiceVO(): InvoiceVO {
        return InvoiceVO(
            stateResource,
            date,
            amount
        )
    }
}