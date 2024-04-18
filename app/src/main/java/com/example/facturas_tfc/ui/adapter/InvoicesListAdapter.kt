package com.example.facturas_tfc.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.facturas_tfc.R
import com.example.facturas_tfc.core.extension.asCurrency
import com.example.facturas_tfc.core.extension.toStringDate
import com.example.facturas_tfc.core.utils.getThemeColor
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import com.example.facturas_tfc.databinding.ItemInvoicesListBinding
import com.google.android.material.R.attr as theme

class InvoicesListAdapter :
    ListAdapter<InvoiceVO, InvoicesListAdapter.InvoicesListViewHolder>(InvoiceDiffCallBack()) {

    companion object {
        private const val TAG = "VIEWNEXT InvoicesListAdapter"
    }

    class InvoicesListViewHolder(
        private val binding: ItemInvoicesListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private val amount = binding.itemAmount
        private val date = binding.itemDate
        private val state = binding.itemState
        fun bindView(invoice: InvoiceVO) {
            Log.d(TAG, invoice.toString())

            date.text = invoice.date.toStringDate("yy MMM yyyy")
            state.text = context.getString(invoice.stateResource)
            if (invoice.stateResource == R.string.invoice_item_pending) {
                state.setTextColor(getThemeColor(theme.colorError, state))
            } else if (invoice.stateResource == R.string.invoice_item_paid) {
                state.visibility = View.GONE
            }
            amount.text = invoice.amount.asCurrency()
        }
    }

    private class InvoiceDiffCallBack : DiffUtil.ItemCallback<InvoiceVO>() {
        override fun areItemsTheSame(oldItem: InvoiceVO, newItem: InvoiceVO): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: InvoiceVO, newItem: InvoiceVO): Boolean =
            oldItem == newItem
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoicesListViewHolder {
        val binding =
            ItemInvoicesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoicesListViewHolder(binding, parent.context)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: InvoicesListViewHolder, position: Int) {
        val invoice = getItem(position)
        holder.bindView(invoice)
    }
}