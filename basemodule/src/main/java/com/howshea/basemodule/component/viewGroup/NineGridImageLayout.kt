package com.howshea.basemodule.component.viewGroup

import android.content.Context
import android.databinding.BindingAdapter
import android.util.AttributeSet
import android.view.ViewGroup
import com.howshea.basemodule.R
import com.howshea.basemodule.extentions.yes
import com.howshea.basemodule.utils.dp
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Created by Howshea
 * on 2018/9/4
 */
class NineGridImageLayout : ViewGroup {
    //行
    private var row = 0
    //列
    private var column = 0
    //间距
    var spacing = context.dp(6)
        set(value) {
            field = value
            requestLayout()
        }
    //单张图片最大宽高
    var singleImgSize = context.dp(210)
        set(value) {
            field = value
            requestLayout()
        }
    //单网格宽高
    private var gridSize = 0

    var imageList = arrayListOf<String>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context
            ?.obtainStyledAttributes(attrs, R.styleable.NineGridImageLayout)
            ?.apply {
                spacing = getDimension(R.styleable.NineGridImageLayout_spacing, spacing.toFloat()).toInt()
                singleImgSize = getDimension(R.styleable.NineGridImageLayout_singleSize, singleImgSize.toFloat()).toInt()
            }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val availableWidth = width - paddingLeft - paddingRight
        gridSize = (availableWidth - spacing * 2) / 3
        if (imageList.size == 1) {
            height = singleImgSize
        } else if (imageList.size > 1) {
            row = ceil(imageList.size / 3f).toInt()
            height = gridSize * row + spacing * (row - 1) + paddingTop + paddingBottom
        }
        setMeasuredDimension(width, height)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
    }

}

@BindingAdapter("app:imageList")
fun setImageList(view: NineGridImageLayout, imageList: List<String>?) {
    imageList?.let {
        view.imageList = ArrayList(it)
    }
}