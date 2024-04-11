package com.example.facturas_tfc.ui.secondPract.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.facturas_tfc.R

class SSDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ss_details, container, false)
    }

    companion object {
        private const val TAG = "VIEWNEXT SSDetailsFragment"
    }
}