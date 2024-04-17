package com.example.facturas_tfc.data.repository.model


import com.example.facturas_tfc.R
import java.time.LocalDate

data class Filter(
    var dates: SelectedDate = SelectedDate(),
    var selectedAmount: SelectedRange = SelectedRange(),
    var amountRange: Range = Range(),
    var state: HashMap<Int, Boolean> = hashMapOf(
        R.string.invoice_item_paid to false,
        R.string.invoice_item_cancelled to false,
        R.string.invoice_item_fixed_fee to false,
        R.string.invoice_item_pending to false,
        R.string.invoice_item_payment_plan to false
    )
) {
    fun setAmountRange(min: Float, max: Float) {
        this.amountRange.min = min
        this.amountRange.max = max
    }

    fun setSelectedAmounts(min: Float?, max: Float?) {
        this.selectedAmount.min = min
        this.selectedAmount.max = max
    }

    fun setState(stateResource: Int, isChecked: Boolean) {
        state[stateResource] = isChecked
    }
}

data class Range(
    var min: Float = 1.0f, var max: Float = 100.0f
)

data class SelectedRange(
    var min: Float? = null, var max: Float? = null
)

data class SelectedDate(
    var from: LocalDate? = null,
    var to: LocalDate? = null,
)
