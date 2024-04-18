package com.example.facturas_tfc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.facturas_tfc.data.repository.model.PracticeVO
import com.example.facturas_tfc.databinding.ItemMainPracticeBinding

class PracticeListAdapter(
    private val onPracticeClick: ((practice: PracticeVO) -> Unit)
) : ListAdapter<PracticeVO, PracticeListAdapter.PracticesListViewHolder>(PracticeDiffCallBack()) {

    inner class PracticesListViewHolder(private val binding: ItemMainPracticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val item = binding.itemMainPractice
        private val name = binding.itemName

        fun bindView(practice: PracticeVO) {
            name.text = practice.name
            item.setOnClickListener {
                onPracticeClick(practice)
            }
        }
    }

    private class PracticeDiffCallBack : DiffUtil.ItemCallback<PracticeVO>() {
        override fun areItemsTheSame(
            oldItem: PracticeVO, newItem: PracticeVO
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: PracticeVO, newItem: PracticeVO
        ): Boolean = oldItem == newItem
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticesListViewHolder {
        val binding =
            ItemMainPracticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PracticesListViewHolder(binding)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PracticesListViewHolder, position: Int) {
        val practice = getItem(position)
        holder.bindView(practice)
    }
}