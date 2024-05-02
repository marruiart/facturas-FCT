package com.marinaruiz.facturas_fct.data.network.invoicesApi.models

import com.marinaruiz.facturas_fct.data.local.models.SSDetailsEntity

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