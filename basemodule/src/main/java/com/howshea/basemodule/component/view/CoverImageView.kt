package com.howshea.basemodule.component.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import com.howshea.basemodule.R

/**
 * Created by 陶海啸
 * on 2018/2/2.
 */
class CoverImageView : AppCompatImageView {
    var ratio = 0f
        set(value) {
            field = value
            invalidate()
        }

    private lateinit var paint: Paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.CoverImageView)
        attributes?.let {
            ratio = it.getFloat(R.styleable.CoverImageView_ratio, 0f)
            if (it.hasValue(R.styleable.CoverImageView_cover)) {
                paint = Paint()
                paint.color = it.getColor(R.styleable.CoverImageView_cover, resources.getColor(android.R.color.transparent))
            }
        }
        attributes?.recycle()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (ratio != 0f) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width / ratio).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (::paint.isInitialized) {
            canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }
}