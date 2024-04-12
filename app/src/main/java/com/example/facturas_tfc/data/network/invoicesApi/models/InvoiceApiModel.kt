package com.example.facturas_tfc.data.network.invoicesApi.models

import com.example.facturas_tfc.data.local.models.InvoiceEntity
import java.time.LocalDate

data class InvoiceApiModel(
    val stateResource: Int,
    val date: LocalDate,
    val amount: Float
) {
    fun asInvoiceEntity(): InvoiceEntity {
        return InvoiceEntity(
            stateResource,
            date,
            amount
        )
    }
}

fun List<InvoiceApiModel>.asInvoiceEntityList() = this.map { it.asInvoiceEntity() }