package com.example.facturas_tfc.data.repository.model

import com.example.facturas_tfc.data.local.models.InvoiceEntity
import java.time.LocalDate

data class InvoiceVO(
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

fun List<InvoiceVO>.asInvoiceEntityList(): List<InvoiceEntity> {
    return this.map {
        it.asInvoiceEntity()
    }
}