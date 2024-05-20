package com.marinaruiz.facturas_fct.ui.firstPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marinaruiz.facturas_fct.R
import com.marinaruiz.facturas_fct.core.DynamicThemeActivity
import com.marinaruiz.facturas_fct.core.utils.AppEnvironment
import com.marinaruiz.facturas_fct.data.network.firebase.RemoteConfigService
import com.marinaruiz.facturas_fct.data.repository.model.Filter
import com.marinaruiz.facturas_fct.data.repository.model.InvoiceVO
import com.marinaruiz.facturas_fct.databinding.ActivityInvoicesListBinding
import com.marinaruiz.facturas_fct.ui.MainActivity
import com.marinaruiz.facturas_fct.ui.adapter.InvoicesListAdapter
import com.marinaruiz.facturas_fct.ui.firstPract.fragment.InvoicesFilterDialogFragment
import com.marinaruiz.facturas_fct.ui.firstPract.fragment.InvoicesFilterDialogFragmentListener
import com.marinaruiz.facturas_fct.ui.firstPract.viewmodel.InvoicesViewModel

class InvoicesListActivity : DynamicThemeActivity(), InvoicesFilterDialogFragmentListener {
    private val remoteConfig = RemoteConfigService.getInstance()
    private lateinit var adapter: InvoicesListAdapter
    private lateinit var toolbar: MaterialToolbar
    private lateinit var binding: ActivityInvoicesListBinding
    private lateinit var filtersDialog: InvoicesFilterDialogFragment
    private val invoicesVM: InvoicesViewModel by viewModels()

    companion object {
        private const val TAG = "VIEWNEXT InvoicesListActivity"

        fun create(context: Context): Intent = Intent(context, InvoicesListActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = remoteConfig.getStringValue()
        setCurrentTheme(theme)
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
        adapter = InvoicesListAdapter(::onInvoiceClick)
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
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.btn_menu_filter -> {
                    showDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun setEnvironmentSwitchListener() {
        binding.switchEnvironment.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    applicationContext, "Entorno cambiado a producción", Toast.LENGTH_SHORT
                ).show()
                invoicesVM.changeEnvironment(AppEnvironment.PROD_ENVIRONMENT)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Entorno cambiado a desarrollo (mock data)",
                    Toast.LENGTH_SHORT
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

    private fun showDialog() {
        filtersDialog = InvoicesFilterDialogFragment(this)
        filtersDialog.show(supportFragmentManager, "InvoicesFilterDialogFragment")
    }

    private fun onInvoiceClick(invoice: InvoiceVO) {
        Log.d(TAG, invoice.toString())
        MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.information))
            .setMessage(resources.getString(R.string.not_available_yet))
            .setNeutralButton(resources.getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onApplyFilters(filter: Filter) {
        invoicesVM.applyFilter(filter)
        filtersDialog.dismiss()
    }

}