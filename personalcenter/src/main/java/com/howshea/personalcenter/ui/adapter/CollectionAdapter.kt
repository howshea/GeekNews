package com.howshea.personalcenter.ui.adapter

import android.app.Activity
import com.bumptech.glide.Glide
import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.basemodule.database.Collection
import com.howshea.basemodule.extentions.yes
import com.howshea.personalcenter.R
import com.howshea.personalcenter.databinding.ItemCollectionAdapterBinding

/**
 * Created by Howshea
 * on 2018/11/2
 */

class CollectionAdapter(items: MutableList<Collection>, private val activity: Activity) : BaseAdapter<Collection, ItemCollectionAdapterBinding>(items, R.layout.item_collection_adapter) {
    override fun bindItem(binding: ItemCollectionAdapterBinding, item: Collection) {
        binding.collection = item
    }

    override fun bindAfterExecute(binding: ItemCollectionAdapterBinding, item: Collection) {
        (item.cover.isNotEmpty()).yes {
            Glide.with(activity)
                .load(item.cover)
                .into(binding.imgCover)
        }
    }
}