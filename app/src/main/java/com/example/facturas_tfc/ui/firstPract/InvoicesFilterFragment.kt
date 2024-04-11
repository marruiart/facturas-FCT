package com.example.facturas_tfc.ui.firstPract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.facturas_tfc.R

class InvoicesFilterFragment : Fragment() {

    companion object {
        private const val TAG = "VIEWNEXT InvoicesFilterFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoices_filter, container, false)
    }
}