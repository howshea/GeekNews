package com.howshea.personalcenter.ui.adapter

import android.graphics.Paint
import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.personalcenter.R
import com.howshea.personalcenter.databinding.ItemSourceAdapterBinding
import com.howshea.personalcenter.model.Source

/**
 * Created by Howshea
 * on 2018/10/31
 */
class SourceAdapter(items: MutableList<Source>) : BaseAdapter<Source, ItemSourceAdapterBinding>(items, R.layout.item_source_adapter) {
    override fun bindItem(binding: ItemSourceAdapterBinding, item: Source) {
        binding.source = item
        binding.tvWebAddress.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}