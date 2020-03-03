package com.howshea.read.ui.adapter

import androidx.fragment.app.Fragment
import android.view.View
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
        if (!(item.cover == "none" || item.cover == null)) {
            Glide.with(fragment)
                .load(item.cover)
                .transition(withCrossFade())
                .apply(RequestOptions().placeholder(R.color.divider))
                .into(binding.imgCover)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        if (holder.binding.imgCover.visibility == View.VISIBLE) {
            Glide.with(fragment).clear(holder.binding.imgCover)
        }
    }
}