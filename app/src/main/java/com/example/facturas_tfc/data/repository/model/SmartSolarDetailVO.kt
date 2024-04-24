package com.example.facturas_tfc.data.repository.model

data class SmartSolarDetailVO(
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
)