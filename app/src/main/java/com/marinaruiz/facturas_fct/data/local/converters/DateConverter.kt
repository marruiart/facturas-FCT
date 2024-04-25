package com.marinaruiz.facturas_fct.data.local.converters

import androidx.room.TypeConverter
import com.marinaruiz.facturas_fct.core.extension.toLocalDate
import com.marinaruiz.facturas_fct.core.extension.toStringDate
import java.time.LocalDate

object DateConverter {

    @TypeConverter
    fun toLocalDate(date: String?): LocalDate? = date?.toLocalDate("dd/MM/yyyy")

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toStringDate("dd/MM/yyyy")

}