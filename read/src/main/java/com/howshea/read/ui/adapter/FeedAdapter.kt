package com.howshea.read.ui.adapter

import android.support.v4.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.read.R
import com.howshea.read.databinding.ItemFeedAdapterBinding
import com.howshea.read.model.Feed

/**
 * Created by Howshea
 * on 2018/10/25
 */
class FeedAdapter(items: MutableList<Feed.Results>, private val fragment: Fragment) : BaseAdapter<Feed.Results, ItemFeedAdapterBinding>(items, R.layout.item_feed_adapter) {
    override fun bindItem(binding: ItemFeedAdapterBinding, item: Feed.Results) {
        binding.feed = item
        Glide.with(fragment)
            .load(item.site.icon)
            .transition(withCrossFade())
            .apply(RequestOptions().placeholder(R.color.divider))
            .into(binding.imgIcon)
        if ("none" != item.cover) {
            Glide.with(fragment)
                .load(item.cover)
                .transition(withCrossFade())
                .apply(RequestOptions().placeholder(R.color.divider))
                .into(binding.imgCover)
        }

    }
}