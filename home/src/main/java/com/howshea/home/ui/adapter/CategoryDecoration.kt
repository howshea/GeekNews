package com.howshea.home.ui.adapter

import android.content.Context
import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.dp
import com.howshea.home.R
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/8/16
 */
class CategoryDecoration(val data: List<Common>, context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }
    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = context.resources.getColor(R.color.colorPrimary)
        textSize = context.dp(11).toFloat()
        textAlign = Paint.Align.LEFT
    }
    private var dividerHeight = context.dp(9)
    private val tagHeight = context.dp(38)
    private val lineHeight = context.dp(0.7f)
    private val paddingLeft = context.dp(16)
    private val radius = context.dp(9)

    override fun getItemOffsets(outRect: Rect?, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position < 0) return
        outRect?.top = when {
            position == 0 -> tagHeight
            isFirstInGroup(position) -> tagHeight + dividerHeight
            else -> lineHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        (0 until childCount).forEach {
            val child = parent.getChildAt(it)
            val index = parent.getChildAdapterPosition(child)
            when {

                isFirstInGroup(index) -> {
                    val top = child.top - tagHeight.toFloat()
                    val bottom = child.top - lineHeight.toFloat()
                    c.drawRect(0f, top, parent.width.toFloat(), bottom, paint)
                    val text = data[index].type
                    val textWidth = textPaint.measureText(text).toInt()
                    val rectF = RectF(paddingLeft.toFloat(), top + AppContext.dp(10), paddingLeft + textWidth + AppContext.dp(13.5f) * 2.0f, top + AppContext.dp(28))
                    c.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        color = Color.parseColor("#F0F3F5")
                    })
                    val textHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top
                    val baseLine = rectF.top + (rectF.height() - textHeight) / 2.0f - textPaint.fontMetrics.top
                    val textLeft = rectF.left + AppContext.dp(13.5f).toFloat()
                    c.drawText(text, textLeft, baseLine, textPaint)

                }
                else -> {
                    val top = (child.top - lineHeight).toFloat()
                    val bottom = child.top.toFloat()
                    c.drawRect(0f, top, paddingLeft.toFloat(), bottom, paint)
                }
            }
        }
    }

    private fun isFirstInGroup(pos: Int): Boolean {
        return pos == 0 || data[pos].type != data[pos - 1].type
    }
}