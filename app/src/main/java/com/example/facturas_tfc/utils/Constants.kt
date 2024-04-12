package com.example.facturas.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Dates {
    val EPOCH_DATE: LocalDate = LocalDate.ofEpochDay(0)
    val NOW: LocalDate = LocalDate.now()
    val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
}