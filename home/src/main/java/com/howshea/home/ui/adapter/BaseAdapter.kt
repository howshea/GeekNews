package com.howshea.home.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Howshea
 * on 2018/9/10
 */
abstract class BaseAdapter<T, B : ViewDataBinding>(private var items: List<T>, private var layoutRes: Int) : RecyclerView.Adapter<BaseAdapter<T, B>.ViewHolder>() {
    private var itemClickListener: ((item: T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<B>(inflater, layoutRes, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setNewData(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: B) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            bindItem(binding, item)
            binding.root.setOnClickListener {
                itemClickListener?.invoke(item)
            }
            binding.executePendingBindings()
        }
    }

    abstract fun bindItem(binding: B, item: T)

    fun setItemClick(click: (item: T) -> Unit) {
        itemClickListener = click
    }
}