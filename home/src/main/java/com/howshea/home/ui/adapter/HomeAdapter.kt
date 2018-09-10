package com.howshea.home.ui.adapter

import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/9/7
 */

class HomeAdapter(items: List<Common>) : BaseAdapter<Common, ItemDailyAdapterBinding>(items, R.layout.item_daily_adapter) {
    override fun bindItem(binding: ItemDailyAdapterBinding, item: Common) {
        binding.common = item
    }
}