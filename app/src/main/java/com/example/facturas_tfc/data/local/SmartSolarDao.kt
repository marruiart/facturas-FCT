package com.example.facturas_tfc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.facturas_tfc.data.local.models.SSDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartSolarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveSmartSolarDetails(entity: SSDetailsEntity)

    @Query("SELECT * FROM smartsolar")
    fun getSmartSolarDetails(): Flow<SSDetailsEntity>

    @Query("DELETE FROM smartsolar")
    suspend fun deleteAll()
}