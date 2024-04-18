package com.example.facturas_tfc.core.utils

import android.view.View
import com.google.android.material.color.MaterialColors


fun getThemeColor(attr: Int, view: View): Int {
    return MaterialColors.getColor(view, attr)
}
