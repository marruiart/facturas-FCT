package com.example.facturas_tfc.data.network.invoicesApi.models

data class SSDetailResponse(
    val detail: Detail
) {
    fun asSSDetailApiModel(): SSDetailApiModel {
        return SSDetailApiModel(
            detail.cau,
            detail.requestStatus,
            detail.selfConsumptionType,
            detail.surplusCompensation,
            detail.installationPower
        )
    }
}

data class Detail(
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
)