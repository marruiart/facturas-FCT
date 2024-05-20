package com.marinaruiz.facturas_fct.ui.secondPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.DynamicThemeActivity
import com.marinaruiz.facturas_fct.data.network.firebase.RemoteConfigService
import com.marinaruiz.facturas_fct.databinding.ActivitySmartSolarBinding
import com.marinaruiz.facturas_fct.ui.MainActivity
import com.marinaruiz.facturas_fct.ui.secondPract.fragment.SSDetailsFragment
import com.marinaruiz.facturas_fct.ui.secondPract.fragment.SSEnergyFragment
import com.marinaruiz.facturas_fct.ui.secondPract.fragment.SSInstallationFragment

class SmartSolarActivity() : DynamicThemeActivity() {
    private val remoteConfig = RemoteConfigService.getInstance()
    private lateinit var binding: ActivitySmartSolarBinding
    private lateinit var toolbar: MaterialToolbar
    private var padding: Int = 0

    companion object {
        const val TAG = "VIEWNEXT SmartSolarActivity"

        fun create(context: Context): Intent = Intent(context, SmartSolarActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = remoteConfig.getStringValue()
        setCurrentTheme(theme)
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
        val image = getThemeImageDrawable(TAG)
        if (image != null) {
            binding.ivThemeImageSs.setImageResource(image)
            binding.ivThemeImageSs.visibility = View.VISIBLE
        }
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