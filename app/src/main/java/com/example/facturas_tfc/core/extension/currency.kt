package com.example.facturas_tfc.core.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Int.asRoundedCurrency(locale: Locale = Locale.getDefault()): String {
    val formatter = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}

fun Int.asCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

fun Float.asCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}