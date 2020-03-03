package com.howshea.personalcenter.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.os.Vibrator
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.toast
import com.howshea.personalcenter.R
import kotlinx.android.synthetic.main.item_sort_adapter.view.*
import java.util.*

/**
 * Created by Howshea
 * on 2018/10/31
 */
class TypeSortAdapter(private val types: MutableList<String>,private val activity:Activity) : RecyclerView.Adapter<TypeSortAdapter.TypeSortViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeSortViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sort_adapter, parent, false)
        return TypeSortViewHolder(view)
    }

    override fun getItemCount() = types.size

    override fun onBindViewHolder(holder: TypeSortViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(types, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class TypeSortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @Suppress("DEPRECATION")
        @SuppressLint("MissingPermission")
        fun bind(position: Int) {
            itemView.tv_type.text = types[position]
            itemView.setOnClickListener {
                AppContext.toast("长按拖拽排序")
            }
            itemView.setOnLongClickListener {
                val service = activity.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                service.vibrate(100)
                false
            }
        }
    }
}