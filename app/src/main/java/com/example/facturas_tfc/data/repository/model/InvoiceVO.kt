package com.example.facturas_tfc.data.repository.model

import java.time.LocalDate

data class InvoiceVO(
    val stateResource: Int,
    val date: LocalDate,
    val amount: Float
)