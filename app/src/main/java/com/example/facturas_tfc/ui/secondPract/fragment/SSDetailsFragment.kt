package com.example.facturas_tfc.ui.secondPract.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.facturas_tfc.data.repository.model.SmartSolarDetailVO
import com.example.facturas_tfc.databinding.FragmentSsDetailsBinding
import com.example.facturas_tfc.ui.secondPract.viewmodel.SmartSolarViewModel

class SSDetailsFragment : Fragment() {
    private val smartSolarVM: SmartSolarViewModel by activityViewModels()
    private lateinit var binding: FragmentSsDetailsBinding

    companion object {
        private const val TAG = "VIEWNEXT SSDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smartSolarVM.details.observe(viewLifecycleOwner) { details ->
            details?.let {
                bindView(it)
            }
        }
    }

    private fun bindView(details: SmartSolarDetailVO) {
        binding.etSsDetailCau.setText(details.cau)
        binding.etSsDetailRequestStatus.setText(details.requestStatus)
        binding.etSsDetailSelfConsumptionTypeLabel.setText(details.selfConsumptionType)
        binding.etSsDetailSurplusCompensation.setText(details.surplusCompensation)
        binding.etSsDetailInstallationPower.setText(details.installationPower)
    }
}