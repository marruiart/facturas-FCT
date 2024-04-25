package com.marinaruiz.facturas_fct.core.utils

import android.view.View
import com.google.android.material.color.MaterialColors


fun getThemeColor(attr: Int, view: View): Int {
    return MaterialColors.getColor(view, attr)
}
