package com.marinaruiz.facturas_fct.core.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.google.android.material.color.MaterialColors


fun getThemeColor(attr: Int, view: View): Int {
    return MaterialColors.getColor(view, attr)
}

@ColorInt
fun Context.themeColor(@AttrRes attrRes: Int): Int =
    TypedValue().apply { theme.resolveAttribute(attrRes, this, true) }.data
