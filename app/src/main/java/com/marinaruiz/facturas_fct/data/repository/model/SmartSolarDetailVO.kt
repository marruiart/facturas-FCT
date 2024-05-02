package com.marinaruiz.facturas_fct.data.repository.model

data class SmartSolarDetailVO(
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
)