package com.example.facturas_tfc.core.dateformat

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(localDate: LocalDate, pattern: String): String {
    val format = DateTimeFormatter.ofPattern(pattern)
    return localDate.format(format)
}