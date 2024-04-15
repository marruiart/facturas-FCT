package com.example.facturas_tfc.ui.firstPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.facturas_tfc.utils.AppEnvironment
import com.example.facturas_tfc.R
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import com.example.facturas_tfc.databinding.ActivityInvoicesListBinding
import com.example.facturas_tfc.ui.MainActivity
import com.example.facturas_tfc.ui.adapter.InvoicesListAdapter
import com.google.android.material.appbar.MaterialToolbar

class InvoicesListActivity : AppCompatActivity() {
    private lateinit var adapter: InvoicesListAdapter
    private lateinit var toolbar: MaterialToolbar
    private lateinit var binding: ActivityInvoicesListBinding
    private val invoicesVM: InvoicesViewModel by viewModels()

    companion object {
        private const val TAG = "VIEWNEXT InvoicesListActivity"

        fun create(context: Context): Intent =
            Intent(context, InvoicesListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInvoicesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setWindowInsets()
        setToolbar()
        initUI()
    }

    private fun setWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setToolbar() {
        toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.invoices_list_toolbar_menu)
    }

    private fun initUI() {
        initRecyclerView()
        initListeners()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = InvoicesListAdapter()
        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {
        binding.invoicesRv.adapter = adapter
    }

    private fun initListeners() {
        setEnvironmentSwitchListener()
        toolbar.setNavigationOnClickListener {
            navigateUpTo(MainActivity.create(this))
        }
    }

    private fun setEnvironmentSwitchListener() {
        binding.switchEnvironment.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "Entorno cambiado a producción", Toast.LENGTH_SHORT).show()
                invoicesVM.changeEnvironment(AppEnvironment.PROD_ENVIRONMENT)
            } else {
                Toast.makeText(
                    applicationContext, "Entorno cambiado a desarrollo (mock data)", Toast.LENGTH_SHORT
                ).show()
                invoicesVM.changeEnvironment(AppEnvironment.MOCK_ENVIRONMENT)
            }
        }
    }

    private fun initObservers() {
        invoicesVM.invoices.observe(this) { invoices ->
            if (invoices.isEmpty()) {
                binding.emptyRv.visibility = View.VISIBLE
                binding.invoicesRv.visibility = View.GONE
            } else {
                populatePracticeList(invoices)
                binding.emptyRv.visibility = View.GONE
                binding.invoicesRv.visibility = View.VISIBLE
            }
        }
    }

    private fun populatePracticeList(invoices: List<InvoiceVO>) {
        Log.d(TAG, invoices.toString())
        adapter.submitList(invoices)
    }

}