package com.marinaruiz.facturas_fct.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.data.repository.model.PracticeVO
import com.marinaruiz.facturas_fct.databinding.ActivityMainBinding
import com.marinaruiz.facturas_fct.ui.adapter.PracticeListAdapter
import com.marinaruiz.facturas_fct.ui.auth.LoginActivity
import com.marinaruiz.facturas_fct.ui.firstPract.InvoicesListActivity
import com.marinaruiz.facturas_fct.ui.navigationPract.WebPagesNavigationActivity
import com.marinaruiz.facturas_fct.ui.secondPract.SmartSolarActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PracticeListAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainViewModel by viewModels()
    private var padding: Int = 0
    private val practices = arrayListOf(
        PracticeVO(1, "Práctica 1"),
        PracticeVO(2, "Práctica 2"),
        PracticeVO(3, "Navegación")
    )

    companion object {
        private const val TAG = "VIEWNEXT MainActivity"

        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        initUI()
    }

    private fun setWindowInsets() {
        padding = resources.getDimension(com.marinaruiz.facturas_fct.R.dimen.dimen_size_16).toInt()

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
    }

    private fun initUI() {
        initRecyclerView()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnLogout.setOnClickListener {
            mainVM.logout()
        }
    }

    private fun initObservers() {
        mainVM.uid.observe(this){ uid ->
            if(uid == null) {
                startActivity(LoginActivity.create(this))
                this.finish()
            }
        }
    }

    private fun initRecyclerView() {
        adapter = PracticeListAdapter(::onPracticeClick)
        setRecyclerViewAdapter()
        populatePracticeList()
    }

    private fun setRecyclerViewAdapter() {
        binding.invoicesRv.adapter = adapter
    }

    private fun populatePracticeList() {
        Log.d(TAG, practices.toString())
        adapter.submitList(practices)
    }

    private fun onPracticeClick(practice: PracticeVO) {
        when (practice.id) {
            1 -> startActivity(InvoicesListActivity.create(this))
            2 -> startActivity(SmartSolarActivity.create(this))
            3 -> startActivity(WebPagesNavigationActivity.create(this))
            else -> Log.d(TAG, "No activity to be started")
        }
    }
}