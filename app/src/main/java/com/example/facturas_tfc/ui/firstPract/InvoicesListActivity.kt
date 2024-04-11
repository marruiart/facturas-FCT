package com.example.facturas_tfc.ui.firstPract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.facturas_tfc.R
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import com.example.facturas_tfc.databinding.ActivityInvoicesListBinding
import com.example.facturas_tfc.ui.adapter.InvoicesListAdapter

class InvoicesListActivity : AppCompatActivity() {
    private lateinit var adapter: InvoicesListAdapter
    private lateinit var binding: ActivityInvoicesListBinding
    private val invoicesVM: InvoicesViewModel by viewModels()

    companion object {
        private const val TAG = "VN InvoicesListActivity"

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
        val toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.invoices_list_toolbar_menu)
    }

    private fun initUI() {
        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        adapter = InvoicesListAdapter()
        setRecyclerViewAdapter()
    }

    private fun setRecyclerViewAdapter() {
        binding.invoicesRv.adapter = adapter
    }

    private fun initListeners() {
        invoicesVM.invoices.observe(this) { invoices ->
            populatePracticeList(invoices)
        }
    }

    private fun populatePracticeList(invoices: List<InvoiceVO>) {
        Log.d(TAG, invoices.toString())
        adapter.submitList(invoices)
    }

}