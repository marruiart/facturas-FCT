package com.example.facturas_tfc.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.facturas.utils.Dates
import com.example.facturas_tfc.data.repository.model.InvoiceVO
import com.example.facturas_tfc.databinding.ItemInvoicesListBinding

class InvoicesListAdapter :
    ListAdapter<InvoiceVO, InvoicesListAdapter.InvoicesListViewHolder>(InvoiceDiffCallBack()) {

    class InvoicesListViewHolder(
        private val binding: ItemInvoicesListBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        private val amount = binding.itemAmount
        private val date = binding.itemDate
        private val state = binding.itemState
        fun bindView(invoice: InvoiceVO) {
            Log.d("INVOICES_ADAPTER", invoice.toString())
            date.text = invoice.date.format(Dates.FORMATTER)
            state.text = context.getString(invoice.stateResource)
            amount.text = invoice.amount.toString()
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
        Log.d("INVOICES_ADAPTER", "onCreateViewHolder")
        val binding =
            ItemInvoicesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InvoicesListViewHolder(binding, parent.context)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: InvoicesListViewHolder, position: Int) {
        Log.d("INVOICES_ADAPTER", "onBindViewHolder")
        val invoice = getItem(position)
        holder.bindView(invoice)
    }
}