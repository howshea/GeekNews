package com.howshea.basemodule.component.viewGroup.baseAdapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Howshea
 * on 2018/9/10
 */
abstract class BaseAdapter<T, B : ViewDataBinding>(private var items: MutableList<T>, private var layoutRes: Int) : RecyclerView.Adapter<BaseAdapter<T, B>.ViewHolder>() {
    private var itemClickListener: ((item: T, binding: B) -> Unit)? = null
    private var isLoading = false

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
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            bindItem(binding, item)
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                itemClickListener?.invoke(item, binding)
            }
            bindAfterExecute(binding, item)
        }

    }

    abstract fun bindItem(binding: B, item: T)

    open fun bindAfterExecute(binding: B, item: T) {}

    fun setItemClick(click: (item: T, binding: B) -> Unit) {
        itemClickListener = click
    }

    fun setLoadMoreListener(recyclerView: RecyclerView, loader: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(ry: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(ry, dx, dy)
                val layoutManager = ry.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItem >= totalItemCount - 10 && dy > 0) {
                    if (!isLoading) {
                        loader()
                        isLoading = true
                    }
                }
            }

            override fun onScrollStateChanged(ry: RecyclerView, newState: Int) {
                super.onScrollStateChanged(ry, newState)
                val layoutManager = ry.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItem >= totalItemCount - 10 && newState == 0) {
                    if (!isLoading) {
                        loader()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun setLoadComplete() {
        isLoading = false
    }

    fun setLoadFail() {
        isLoading = false
    }

    fun addData(data: List<T>) {
        items.addAll(data)
        notifyItemRangeInserted(items.size - data.size, data.size)
    }
}