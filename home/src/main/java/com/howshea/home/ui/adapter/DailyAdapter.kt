package com.howshea.home.ui.adapter

import android.databinding.BindingAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.howshea.basemodule.AppContext
import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

class DailyAdapter(data: List<Common>) : BaseQuickAdapter<Common, DailyAdapter.ViewHolder>(data) {

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