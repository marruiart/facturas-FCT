package com.marinaruiz.facturas_fct.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marinaruiz.facturas_fct.data.local.models.InvoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveInvoices(entity: List<InvoiceEntity>)

    @Query("SELECT * FROM invoice")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>

    @Query("DELETE FROM invoice")
    suspend fun deleteAll()

    @Query("SELECT (SELECT COUNT(*) FROM invoice) == 0")
    suspend fun isEmpty(): Boolean
}