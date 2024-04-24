package com.example.facturas_tfc.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.facturas_tfc.data.repository.model.SmartSolarDetailVO

@Entity(tableName = "smartsolar")
data class SSDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val cau: String,
    val requestStatus: String,
    val selfConsumptionType: String,
    val surplusCompensation: String,
    val installationPower: String
) {
    fun asSmartSolarDetailVO(): SmartSolarDetailVO {
        return SmartSolarDetailVO(
            cau,
            requestStatus,
            selfConsumptionType,
            surplusCompensation,
            installationPower
        )
    }
}
