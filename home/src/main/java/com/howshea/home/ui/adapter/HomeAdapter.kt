package com.howshea.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

class HomeAdapter(data: List<Common>) : BaseQuickAdapter<Common, HomeAdapter.ViewHolder>(data) {

    override fun convert(helper: ViewHolder, item: Common) {
        val binding = helper.getBinding()
        binding.common = item
        binding.executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDailyAdapterBinding.inflate(inflater, parent, false)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        fun getBinding() =
            itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ItemDailyAdapterBinding
    }
}