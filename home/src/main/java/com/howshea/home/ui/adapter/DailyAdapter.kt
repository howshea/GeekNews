package com.howshea.home.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

class DailyAdapter(private var items: List<Common>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDailyAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(newItems: List<Common>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDailyAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Common) {
            binding.common = item
            binding.executePendingBindings()
        }
    }
}