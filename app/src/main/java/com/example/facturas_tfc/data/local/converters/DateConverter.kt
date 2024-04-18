package com.example.facturas_tfc.data.local.converters

import androidx.room.TypeConverter
import com.example.facturas_tfc.core.extension.toLocalDate
import com.example.facturas_tfc.core.extension.toStringDate
import java.time.LocalDate

object DateConverter {

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? = date?.toLocalDate("dd/MM/yyyy")

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toStringDate("dd/MM/yyyy")

}