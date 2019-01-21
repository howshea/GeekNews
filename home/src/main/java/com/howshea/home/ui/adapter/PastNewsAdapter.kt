package com.howshea.home.ui.adapter

import android.content.Context
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.home.R
import com.howshea.home.databinding.ItemPastNewsBinding
import com.howshea.home.model.HistoryResult

/**
 * Created by Howshea
 * on 2018/9/19
 */
class PastNewsAdapter(items: MutableList<HistoryResult>, private val context: Context) : BaseAdapter<HistoryResult, ItemPastNewsBinding>(items, R.layout.item_past_news) {

	override fun bindItem(binding: ItemPastNewsBinding, item: HistoryResult) {
		binding.history = item
		Glide.with(context)
			.load(item.cover)
			.transition(withCrossFade())
			.apply(RequestOptions().placeholder(R.color.divider).error(R.color.divider))
			.into(binding.imgCover)
	}


	override fun onViewRecycled(holder: ViewHolder) {
		super.onViewRecycled(holder)
		Glide.with(context).clear(holder.binding.imgCover)
	}
}