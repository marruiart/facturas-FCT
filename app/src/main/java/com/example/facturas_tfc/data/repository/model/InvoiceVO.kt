package com.example.facturas_tfc.data.repository.model

import com.example.facturas.utils.Dates
import com.example.facturas_tfc.data.local.models.InvoiceEntity
import java.time.LocalDate

data class InvoiceVO(
    val stateResource: Int, val date: LocalDate, val amount: Float
) {
    fun asInvoiceEntity(): InvoiceEntity {
        return InvoiceEntity(
            stateResource, date, amount
        )
    }
}

fun List<InvoiceVO>.asInvoiceEntityList(): List<InvoiceEntity> {
    return this.map {
        it.asInvoiceEntity()
    }
}

fun List<InvoiceVO>.applyFilter(filter: Filter): List<InvoiceVO> {
    return this.filter { invoice ->
        checkFilterByDate(invoice, filter) && checkFilterByAmount(invoice, filter) && checkFilterByState(invoice, filter)
    }
}

private fun checkFilterByDate(invoice: InvoiceVO, filter: Filter): Boolean {
    return invoice.date in (filter.dates.from ?: Dates.EPOCH_DATE)..(filter.dates.to ?: Dates.NOW)
}

private fun checkFilterByAmount(invoice: InvoiceVO, filter: Filter): Boolean {
    return invoice.amount in (filter.selectedAmount.min
        ?: filter.amountRange.min)..(filter.selectedAmount.max ?: filter.amountRange.max)
}

private fun checkFilterByState(invoice: InvoiceVO, filter: Filter): Boolean {
    return if (filter.state.values.contains(true)) {
        filter.state.getOrDefault(invoice.stateResource, true)
    } else {
        true
    }
}