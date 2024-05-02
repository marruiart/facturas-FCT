package com.marinaruiz.facturas_fct.ui.secondPract.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marinaruiz.facturas_fct.R

class SSEnergyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ss_energy, container, false)
    }

    companion object {
      private const val TAG = "VIEWNEXT SSEnergyFragment"
    }
}