package com.marinaruiz.facturas_fct.core.extension

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toStringDate(pattern: String, locale: Locale? = Locale.getDefault()): String {
    return if (locale != null) {
        this.format(DateTimeFormatter.ofPattern(pattern, locale))
    } else {
        this.format(DateTimeFormatter.ofPattern(pattern))
    }
}

fun String.toLocalDate(pattern: String, locale: Locale? = Locale.getDefault()): LocalDate {
    return if (locale != null) {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern, locale))
    } else {
        LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
    }
}

fun String.toZonedDateTime(
    pattern: String = "yyyy-MM-dd'T'HH:mm:ssXXX", locale: Locale? = Locale.getDefault()
): ZonedDateTime {
    return if (locale != null) {
        ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern, locale))
    } else {
        ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
    }
}

fun ZonedDateTime.toIsoDateFormat(locale: Locale? = Locale.getDefault()): String {
    val isoPattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
    return if (locale != null) {
        this.format(DateTimeFormatter.ofPattern(isoPattern, locale))
    } else {
        this.format(DateTimeFormatter.ofPattern(isoPattern))
    }
}