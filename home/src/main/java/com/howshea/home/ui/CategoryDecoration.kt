package com.howshea.home.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.utils.sp
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/8/16
 */
class CategoryDecoration(var data: List<Common>, context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        textSize = titleSize.toFloat()
        isAntiAlias = true
        color = Color.parseColor("#f5f5f5")
    }
    private val bounds = Rect()
    private var titleHeight = AppContext.dp(30)
    private var titleSize = AppContext.sp(16)
    private val inflater = LayoutInflater.from(context)
    private var dividerHeight = 0

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            dividerHeight = AppContext.dp(1)
            outRect?.top = dividerHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        (0 until childCount).forEach {
            val child = parent.getChildAt(it)
            val index = parent.getChildAdapterPosition(child)
            if (index == 0) {
                return@forEach
            }
            val dividerTop = (child.top - dividerHeight).toFloat()
            val dividerLeft =  AppContext.dp(16).toFloat()
            val dividerBottom = child.top.toFloat()
            val dividerRight = (parent.width - parent.paddingRight).toFloat()

            c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,paint)
        }
    }
}