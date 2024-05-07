package com.marinaruiz.facturas_fct.core

import androidx.appcompat.app.AppCompatActivity
import com.marinaruiz.facturas_fct.R

abstract class DynamicThemeActivity : AppCompatActivity() {

    companion object {
        const val KEY_THEME = "newTheme"
        val DEFAULT_THEME = R.style.Base_Theme_FacturasFCT
        val HALLOWEEN_THEME = R.style.Base_Theme_FacturasFCT_Halloween
        private var currentTheme = DEFAULT_THEME
    }

    protected fun setTheme() {
        setTheme(currentTheme)
    }

    protected fun setCurrentTheme(theme: Int) {
        currentTheme = theme
        setTheme()
    }

    protected fun setCurrentTheme(key: String) {
        val themes = mapOf(
            "DEFAULT_THEME" to R.style.Base_Theme_FacturasFCT,
            "HALLOWEEN_THEME" to R.style.Base_Theme_FacturasFCT_Halloween
        )
        currentTheme = checkNotNull(themes[key])
        setTheme()
    }
}