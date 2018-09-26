package com.howshea.home.ui.adapter

import android.view.View
import com.howshea.basemodule.BaseAdapter
import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/9/7
 */

class HomeAdapter(items: List<Common>) : BaseAdapter<Common, ItemDailyAdapterBinding>(items, R.layout.item_daily_adapter) {
    private var imageClickListener: ((v: View, position: Int, imageList: List<String>) -> Unit)? = null


    override fun bindItem(binding: ItemDailyAdapterBinding, item: Common) {
        binding.common = item
        binding.layoutNineGrid.onItemClick { v, position ->
            imageClickListener?.invoke(v, position, item.images!!)
        }
    }

    fun setOnImageClick(click: (v: View, position: Int, imageList: List<String>) -> Unit) {
        imageClickListener = click
    }
}