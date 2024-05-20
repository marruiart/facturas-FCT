package com.marinaruiz.facturas_fct.core

import androidx.appcompat.app.AppCompatActivity
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.ui.MainActivity
import com.marinaruiz.facturas_fct.ui.auth.LoginActivity
import com.marinaruiz.facturas_fct.ui.secondPract.SmartSolarActivity

abstract class DynamicThemeActivity : AppCompatActivity() {

    companion object {
        const val KEY_THEME = "newTheme"
        val DEFAULT_THEME = R.style.Base_Theme_FacturasFCT
        val HALLOWEEN_THEME = R.style.Base_Theme_FacturasFCT_Halloween
        val CHRISTMAS_THEME = R.style.Base_Theme_FacturasFCT_Christmas
        private var currentTheme = DEFAULT_THEME
    }

    protected fun setTheme() {
        setTheme(currentTheme)
    }

    protected fun setCurrentTheme(theme: Int) {
        currentTheme = theme
        setTheme()
    }

    protected fun getThemeImageDrawable(tag: String): Int? {
        return when (currentTheme) {
            HALLOWEEN_THEME -> {
                when (tag) {
                    LoginActivity.TAG -> R.drawable.halloween_pumpkin
                    MainActivity.TAG -> R.drawable.halloween_cauldron
                    SmartSolarActivity.TAG -> R.drawable.halloween_ghost
                    else -> null
                }
            }

            CHRISTMAS_THEME -> {
                when (tag) {
                    LoginActivity.TAG -> R.drawable.christmas_santaclaus
                    MainActivity.TAG -> R.drawable.christmas_tree
                    SmartSolarActivity.TAG -> R.drawable.christmas_hat
                    else -> null
                }
            }

            DEFAULT_THEME -> {
                return null
            }

            else -> null
        }

    }

    protected fun setCurrentTheme(key: String) {
        val themes = mapOf(
            "DEFAULT_THEME" to R.style.Base_Theme_FacturasFCT,
            "HALLOWEEN_THEME" to R.style.Base_Theme_FacturasFCT_Halloween,
            "CHRISTMAS_THEME" to R.style.Base_Theme_FacturasFCT_Christmas
        )
        currentTheme = checkNotNull(themes[key])
        setTheme()
    }
}