package com.howshea.home.ui.adapter

import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/9/7
 */

class HomeAdapter(items: List<Common>) : BaseAdapter<Common, ItemDailyAdapterBinding>(items, R.layout.item_daily_adapter) {
    private var itemClickListener: ((url: String) -> Unit)? = null

    override fun bindItem(binding: ItemDailyAdapterBinding, item: Common) {
        binding.common = item
        binding.layoutNineGrid.setOnClickListener {

        }
        binding.root.setOnClickListener {
            itemClickListener?.invoke(item.url)
        }
    }

    fun setItemClick(click: (url: String) -> Unit) {
        itemClickListener = click
    }
}