package com.marinaruiz.facturas_fct.data.local.models

import androidx.room.Entity
import com.marinaruiz.facturas_fct.data.repository.model.InvoiceVO
import java.time.LocalDate

@Entity(tableName = "invoice", primaryKeys = ["date", "amount"])
data class InvoiceEntity(
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

fun List<InvoiceEntity>.asInvoiceVOList(): List<InvoiceVO> {
    return this.map {
        it.asInvoiceVO()
    }
}