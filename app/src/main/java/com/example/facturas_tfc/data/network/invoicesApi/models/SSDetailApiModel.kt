package com.example.facturas_tfc.data.network.invoicesApi.models

import com.example.facturas_tfc.data.local.models.SSDetailsEntity

data class SSDetailApiModel(
    val cau: String = "",
    val requestStatus: String = "",
    val selfConsumptionType: String = "",
    val surplusCompensation: String = "",
    val installationPower: String = ""
) {
    fun asSmartSolarDetailsEntity(): SSDetailsEntity {
        return SSDetailsEntity(
            cau = cau,
            requestStatus = requestStatus,
            selfConsumptionType = selfConsumptionType,
            surplusCompensation = surplusCompensation,
            installationPower = installationPower
        )
    }
}