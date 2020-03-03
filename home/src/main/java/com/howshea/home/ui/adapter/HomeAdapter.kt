package com.howshea.home.ui.adapter

import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.howshea.basemodule.component.viewGroup.NineGridImageLayout
import com.howshea.basemodule.component.viewGroup.baseAdapter.BaseAdapter
import com.howshea.home.R
import com.howshea.home.databinding.ItemDailyAdapterBinding
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/9/7
 */

class HomeAdapter(items: MutableList<Common>, private val fragment: Fragment) : BaseAdapter<Common, ItemDailyAdapterBinding>(items, R.layout.item_daily_adapter) {
    private var imageClickListener: ((v: View, position: Int, imageList: List<String>) -> Unit)? = null


    override fun bindItem(binding: ItemDailyAdapterBinding, item: Common) {
        binding.common = item
    }

    override fun bindAfterExecute(binding: ItemDailyAdapterBinding, item: Common) {
        binding.layoutNineGrid.run {
            setImageList(item.images, item.ratio)
            onItemClick { v, position ->
                imageClickListener?.invoke(v, position, item.images!!)
            }
            loadImages { view, url ->
                Glide.with(fragment)
                    .load(url)
                    .transition(withCrossFade())
                    .apply(RequestOptions().placeholder(R.color.divider)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .into(view)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.layoutNineGrid.clearImages {
            Glide.with(fragment).clear(it)
        }
    }

    fun setOnImageClick(click: (v: View, position: Int, imageList: List<String>) -> Unit) {
        imageClickListener = click
    }

    private fun NineGridImageLayout.setImageList(imageList: List<String>?, ratio: Float) {
        //如果为空或者长度为0，就什么都不做
        imageList?.isNotEmpty()?.let {
            this.setData(imageList, ratio)
        }
    }
}