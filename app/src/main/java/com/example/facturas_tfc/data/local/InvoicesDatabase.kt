package com.example.facturas_tfc.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.facturas_tfc.data.local.converters.DateConverter
import com.example.facturas_tfc.data.local.models.InvoiceEntity
import com.example.facturas_tfc.di.App

@Database(entities = [InvoiceEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class InvoicesDatabase : RoomDatabase() {

    companion object {
        @Volatile // Avoid concurrency issues
        private var _INSTANCE: InvoicesDatabase? = null

        fun getInstance(): InvoicesDatabase {
            return _INSTANCE ?: synchronized(this) {
                _INSTANCE ?: buildDatabase().also { db -> _INSTANCE = db }
            }
        }

        private fun buildDatabase(): InvoicesDatabase {
            return Room.databaseBuilder(
                App.context, // context
                InvoicesDatabase::class.java, // db
                "invoices_db" // db name
            ).build()
        }
    }

    abstract fun invoicesDao(): InvoiceDao
}