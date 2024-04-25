package com.marinaruiz.facturas_fct.core.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toStringDate(pattern: String): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

fun String.toLocalDate(pattern: String): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))