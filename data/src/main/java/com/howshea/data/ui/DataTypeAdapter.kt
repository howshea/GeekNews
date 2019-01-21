package com.howshea.data.ui

import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.data.BR
import com.howshea.data.R
import com.howshea.data.databinding.ItemTypeAdapterBinding
import com.howshea.data.model.Data

/**
 * Created by Howshea
 * on 2018/10/29.
 */
class DataTypeAdapter(items: MutableList<Data.Results>) : BaseAdapter<Data.Results, ItemTypeAdapterBinding>(items, R.layout.item_type_adapter) {
    override fun bindItem(binding: ItemTypeAdapterBinding, item: Data.Results) {
        binding.data = item
    }
}