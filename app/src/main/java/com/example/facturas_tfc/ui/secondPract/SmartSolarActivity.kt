package com.example.facturas_tfc.ui.secondPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.facturas_tfc.R
import com.example.facturas_tfc.databinding.ActivitySmartSolarBinding
import com.example.facturas_tfc.ui.MainActivity
import com.example.facturas_tfc.ui.secondPract.fragment.SSDetailsFragment
import com.example.facturas_tfc.ui.secondPract.fragment.SSEnergyFragment
import com.example.facturas_tfc.ui.secondPract.fragment.SSInstallationFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout

class SmartSolarActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySmartSolarBinding
    private lateinit var toolbar: MaterialToolbar
    private var padding: Int = 0

    companion object {
        private const val TAG = "VIEWNEXT SmartSolarActivity"

        fun create(context: Context): Intent = Intent(context, SmartSolarActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySmartSolarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        toolbar = binding.ssIncludeToolbar.mtoolbarBackButton
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(R.dimen.dimen_size_16).toInt()

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mtoolbar_back_button)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun initUI() {
        loadFragment(SSInstallationFragment())
        initListeners()
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            navigateUpTo(MainActivity.create(this))
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (tab.position) {
                        0 -> loadFragment(SSInstallationFragment())
                        1 -> loadFragment(SSEnergyFragment())
                        2 -> loadFragment(SSDetailsFragment())
                        else -> Log.e(TAG, "Tab selection error")
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                print(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                print(tab)
            }
        })
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.ss_fragment_container, fragment)
            .commit()
    }

}