package com.howshea.basemodule.component.viewGroup.baseAdapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.dp

/**
 * Created by Howshea
 * on 2018/10/26
 */
class SimpleDecoration(private var paddingLeft: Int = AppContext.dp(16)) : RecyclerView.ItemDecoration() {


    private val paint = Paint().apply {
        color = Color.WHITE
    }
    private val lineHeight = AppContext.dp(0.7f)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position < 0) return
        outRect.bottom = lineHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (paddingLeft > 0) {
            val childCount = parent.childCount
            (0 until childCount).forEach {
                val child = parent.getChildAt(it)
                val top = child.bottom.toFloat()
                val bottom = (child.bottom + lineHeight).toFloat()
                c.drawRect(0f, top, paddingLeft.toFloat(), bottom, paint)
            }
        }
    }
}