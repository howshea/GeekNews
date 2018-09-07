package com.howshea.home.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.utils.sp
import com.howshea.home.R
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/8/16
 */
class CategoryDecoration(var data: List<Common>, context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        textSize = titleSize.toFloat()
        isAntiAlias = true
        @Suppress("DEPRECATION")
        color = context.resources.getColor(R.color.divider)
    }
    private val bounds = Rect()
    private var titleHeight = AppContext.dp(30)
    private var titleSize = AppContext.sp(16)
    private val inflater = LayoutInflater.from(context)
    private var dividerHeight = 0

    override fun getItemOffsets(outRect: Rect?, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (position < 0) return
        dividerHeight = when {
            position == 0 -> AppContext.dp(38)
            data[position].type != data[position - 1].type -> AppContext.dp(47)
            else -> AppContext.dp(0.7f)
        }
        outRect?.top = dividerHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
//        (0 until childCount).forEach {
//            val child = parent.getChildAt(it)
//            val index = parent.getChildAdapterPosition(child)
//            if (index == 0) {
//                return@forEach
//            }
//            val dividerTop = (child.top - dividerHeight).toFloat()
//            val dividerLeft = if (dividerHeight == AppContext.dp(1)) AppContext.dp(16).toFloat() else 0f
//            val dividerBottom = child.top.toFloat()
//            val dividerRight = (parent.width - parent.paddingRight).toFloat()
//            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, paint)
//        }
    }
}