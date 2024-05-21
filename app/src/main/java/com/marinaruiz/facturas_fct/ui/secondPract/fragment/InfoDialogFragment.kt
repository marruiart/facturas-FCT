package com.marinaruiz.facturas_fct.ui.secondPract.fragment

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.marinaruiz.facturas_fct.databinding.DialogSsDetailsInfoBinding

class InfoDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSsDetailsInfoBinding

    companion object {
        const val TAG = "VIEWNEXT InfoDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogSsDetailsInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        setWidthPercent()
    }

    private fun setWidthPercent() {
        val percent = 97f / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initListeners() {
        binding.btnInfoAccept.setOnClickListener {
            this.dismiss()
        }
    }

}