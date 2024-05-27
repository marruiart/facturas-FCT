package com.marinaruiz.facturas_fct.di

import android.content.Context
import com.marinaruiz.facturas_fct.data.local.InvoiceDao
import com.marinaruiz.facturas_fct.data.local.InvoicesDatabase
import com.marinaruiz.facturas_fct.data.local.SmartSolarDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): InvoicesDatabase {
        return InvoicesDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun smartSolarDao(database: InvoicesDatabase): SmartSolarDao {
        return database.smartSolarDao()
    }

    @Singleton
    @Provides
    fun invoicesDao(database: InvoicesDatabase): InvoiceDao {
        return database.invoicesDao()
    }

}