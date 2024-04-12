package com.example.facturas_tfc.data.local.converters

import androidx.room.TypeConverter
import com.example.facturas.utils.Dates
import java.time.LocalDate

object DateConverter {

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return if (dateString == null) null else LocalDate.parse(dateString, Dates.FORMATTER)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(Dates.FORMATTER)
    }
}