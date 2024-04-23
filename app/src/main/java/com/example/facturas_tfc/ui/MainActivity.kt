package com.example.facturas_tfc.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.facturas_tfc.R
import com.example.facturas_tfc.data.repository.model.PracticeVO
import com.example.facturas_tfc.databinding.ActivityMainBinding
import com.example.facturas_tfc.ui.adapter.PracticeListAdapter
import com.example.facturas_tfc.ui.firstPract.InvoicesListActivity
import com.example.facturas_tfc.ui.navigationPract.WebPagesNavigationActivity
import com.example.facturas_tfc.ui.secondPract.SmartSolarActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PracticeListAdapter
    private lateinit var binding: ActivityMainBinding
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
    }

    private fun initUI() {
        initRecyclerView()
        initListeners()
    }

    private fun initListeners() {
        // TODO init listeners
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