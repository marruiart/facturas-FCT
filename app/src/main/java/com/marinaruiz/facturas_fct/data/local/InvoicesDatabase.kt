package com.marinaruiz.facturas_fct.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marinaruiz.facturas_fct.data.local.converters.DateConverter
import com.marinaruiz.facturas_fct.data.local.models.InvoiceEntity
import com.marinaruiz.facturas_fct.data.local.models.SSDetailsEntity
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [InvoiceEntity::class, SSDetailsEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class InvoicesDatabase : RoomDatabase() {

    companion object {
        @Volatile // Avoid concurrency issues
        private var _INSTANCE: InvoicesDatabase? = null

        fun getInstance(@ApplicationContext context: Context): InvoicesDatabase {
            return _INSTANCE ?: synchronized(this) {
                _INSTANCE ?: buildDatabase(context).also { db -> _INSTANCE = db }
            }
        }

        private fun buildDatabase(context: Context): InvoicesDatabase {
            return Room.databaseBuilder(
                context,
                InvoicesDatabase::class.java, // db
                "invoices_db" // db name
            ).build()
        }
    }

    abstract fun smartSolarDao(): SmartSolarDao
    abstract fun invoicesDao(): InvoiceDao
}