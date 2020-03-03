package com.howshea.personalcenter.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

/**
 * Created by Howshea
 * on 2018/10/31
 */
class TypeDragHelper(callback: DragCallback) : ItemTouchHelper(callback)

class DragCallback(private val adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}