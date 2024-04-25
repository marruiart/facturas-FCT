package com.marinaruiz.facturas_fct.data.network.invoicesApi.models

import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.extension.toLocalDate

data class InvoiceResponse(
    val descEstado: String,
    val fecha: String,
    val importeOrdenacion: Float
) {
    fun asApiModel(): InvoiceApiModel {
        return InvoiceApiModel(
            stateResource = getTranslatedState(descEstado),
            date = fecha.toLocalDate("dd/MM/yyyy"),
            amount = importeOrdenacion
        )
    }

    private fun getTranslatedState(state: String): Int {
        return when (state) {
            "Pagada" -> R.string.invoice_item_paid
            "Anulada" -> R.string.invoice_item_cancelled
            "Cuota fija" -> R.string.invoice_item_fixed_fee
            "Pendiente de pago" -> R.string.invoice_item_pending
            "Plan de pago" -> R.string.invoice_item_payment_plan
            else -> R.string.invoice_item_state_empty
        }
    }
}