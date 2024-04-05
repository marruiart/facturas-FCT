package com.example.facturas_tfc.ui.secondPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.facturas_tfc.R

class SmartSolarActivity() : AppCompatActivity() {
    private var padding: Int = 0

    companion object {
        private const val TAG = "VN SmartSolarActivity"

        fun create(context: Context): Intent = Intent(context, SmartSolarActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smart_solar)
        setWindowInsets()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(com.example.facturas_tfc.R.dimen.dimen_size_16).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mtoolbar_smart_solar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}