package com.howshea.basemodule.component.viewGroup

import android.content.Context
import android.databinding.BindingAdapter
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.howshea.basemodule.R
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.view.SWImageView
import kotlin.math.ceil

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
    //url list
    var imageList = arrayListOf<String>()
    var radio = 0f

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
        val availableWidth = width - paddingLeft - paddingRight
        gridSize = (availableWidth - spacing * 2) / 3
        val height =
            if (imageList.size == 1) {
                if (radio > 1)
                    (singleImgSize / radio).toInt() + paddingTop + paddingBottom
                else
                    singleImgSize + paddingTop + paddingBottom
            } else if (imageList.size > 1) {
                gridSize * row + spacing * (row - 1) + paddingTop + paddingBottom
            } else {
                MeasureSpec.getSize(heightMeasureSpec)
            }
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, p0: Int, p1: Int, p2: Int, p3: Int) {
        if (imageList.isEmpty())
            return
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        when (imageList.size) {
            1 -> {
                val view = getChildAt(0) as ImageView
                right = paddingLeft + view.layoutParams.width
                bottom = paddingTop + view.layoutParams.height
                view.layout(paddingLeft, paddingTop, right, bottom)
                Log.i("你好", "${view.layoutParams.width} $height")
                Glide.with(context)
                    .load(imageList[0])
                    .into(view)
            }
            4 -> {

            }
            else -> {
                var row: Int
                var column: Int
                imageList.forEachIndexed { index, s ->
                    val view = getChildAt(index) as ImageView
                    row = index / 3
                    column = index % 3
                    left = (gridSize + spacing) * column + paddingLeft
                    top = (gridSize + spacing) * row + paddingTop
                    right = left + gridSize
                    bottom = top + gridSize
                    view.layout(left,top,right,bottom)
                    Glide.with(context)
                        .load(s)
                        .into(view)
                }
            }
        }
    }


    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    private fun View.addSystemView() {
        val vlp = this.layoutParams
        val lp: LayoutParams
        lp = if (vlp == null) {
            generateDefaultLayoutParams()
        } else if (!checkLayoutParams(vlp)) {
            generateLayoutParams(vlp) as LayoutParams
        } else {
            vlp as LayoutParams
        }
        addView(this, lp)
    }

    fun setData(imageList: List<String>, radio: Float) {
        this.imageList = ArrayList(imageList)
        this.radio = radio
        removeAllViews()
        //行数
        row = ceil(imageList.size / 3f).toInt()
        if (imageList.size == 1) {
            val subView = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = if (radio > 1) {
                    val height = (singleImgSize / radio).toInt()
                    val width = singleImgSize
                    LayoutParams(width, height)
                } else {
                    val height = singleImgSize
                    val width = (singleImgSize * radio).toInt()
                    LayoutParams(width, height)
                }

            }
            subView.addSystemView()
        } else {
            imageList.forEach {
                val subView = ImageView(context).apply { scaleType = ImageView.ScaleType.CENTER_CROP }
                subView.addSystemView()
            }
        }
        requestLayout()
    }
}

@BindingAdapter("app:imageList", "app:radio")
fun setImageList(view: NineGridImageLayout, imageList: List<String>?, radio: Float) {
    //如果为空或者长度为0，就什么都不做
    imageList?.isNotEmpty()?.let {
        view.setData(imageList, radio)
    }
}