package com.howshea.home.ui.adapter

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import android.view.View
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
    private val textPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint().apply {
            isAntiAlias = true
            color = context.resources.getColor(R.color.colorPrimary)
            textSize = context.dp(12).toFloat()
            textAlign = Paint.Align.LEFT
        }
    }
    private val tagPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.resources.getColor(R.color.bg_tag)
        }
    }
    private var dividerHeight = context.dp(9)
    private val tagBgHeight = context.dp(38)
    private val tagHeight = context.dp(22)
    private val tagPadding = context.dp(12.5f)
    private val lineHeight = context.dp(0.7f)
    private val paddingLeft = context.dp(16)
    private val radius = context.dp(11)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position < 0) return
        outRect.top = when {
            position == 0 -> tagBgHeight
            isFirstInGroup(position) -> tagBgHeight + dividerHeight
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
                    //绘制白色矩形背景
                    val top = child.top - tagBgHeight.toFloat()
                    val bottom = child.top - lineHeight.toFloat()
                    c.drawRect(0f, top, parent.width.toFloat(), bottom, paint)
                    //绘制 Group 标签
                    val text = data[index].type
                    val textWidth = textPaint.measureText(text).toInt()
                    //这里用 child.top 而不用 bottom，是为了让字体视觉上更居中
                    val tagTop = top + (child.top - top - tagHeight) / 2f
                    val rectF = RectF(paddingLeft.toFloat(), tagTop, paddingLeft + textWidth + tagPadding * 2.0f, tagTop + tagHeight)
                    c.drawRoundRect(rectF, radius.toFloat(), radius.toFloat(), tagPaint)
                    val textHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top
                    val baseLine = top + (bottom - top - textHeight) / 2.0f - textPaint.fontMetrics.top
                    val textLeft = rectF.left + tagPadding.toFloat()
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