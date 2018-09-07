package com.howshea.basemodule.component.viewGroup

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.R
import com.howshea.basemodule.utils.RoundedCorners
import com.howshea.basemodule.utils.dp
import kotlin.math.ceil

/**
 * Created by Howshea
 * on 2018/9/4
 */
class NineGridImageLayout : ViewGroup {
    //行
    private var rowCount = 3
    //列
    private var columnCount = 3
    //间距
    var spacing = context.dp(6)
        set(value) {
            field = value
            requestLayout()
        }
    //单张图片最大宽高
    private var singleImgSize = context.dp(210)
        set(value) {
            field = value
            requestLayout()
        }
    //单网格宽高
    private var gridSize = 0
    //url list
    private var imageList = arrayListOf<String>()
    //单图宽高比
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
                gridSize * rowCount + spacing * (rowCount - 1) + paddingTop + paddingBottom
            } else {
                MeasureSpec.getSize(heightMeasureSpec)
            }
        setMeasuredDimension(width, height)

        for (i in 0 until childCount) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec)
        }
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
                val viewGroup = getChildAt(0) as FrameLayout
                right = paddingLeft + viewGroup.layoutParams.width
                bottom = paddingTop + viewGroup.layoutParams.height
                viewGroup.layout(paddingLeft, paddingTop, right, bottom)
                viewGroup.getChildAt(0).layout(0,0,right,bottom)
                viewGroup.findViewById<AppCompatImageView>(R.id.iv_item).loadImage(imageList[0])
            }
            else -> {
                var row: Int
                var column: Int
                imageList.forEachIndexed { index, s ->
                    val viewGroup = getChildAt(index) as FrameLayout

                    //图片数量为4的时候第二张需要换行
                    row = index / (if (imageList.size == 4) 2 else 3)

                    column = index % columnCount
                    left = (gridSize + spacing) * column + paddingLeft
                    top = (gridSize + spacing) * row + paddingTop
                    right = left + gridSize
                    bottom = top + gridSize
                    viewGroup.layout(left, top, right, bottom)
                    viewGroup.getChildAt(0).layout(0,0,gridSize,gridSize)
                    viewGroup.findViewById<AppCompatImageView>(R.id.iv_item).loadImage(s)
                }
            }
        }
    }

    private fun ImageView.loadImage(s: String) {
        Glide.with(context)
            .load(s)
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(dp(3),dp(0.7f))))
            .into(this)
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

    @SuppressLint("InflateParams")
    fun setData(imageList: List<String>, radio: Float) {
        this.imageList = ArrayList(imageList)
        this.radio = radio
        //清除所有子view ，避免 recyclerView 复用导致错乱问题
        removeAllViews()
        //行数
        rowCount = ceil(imageList.size / 3f).toInt()
        //列数
        columnCount = if (imageList.size == 4) 2 else 3
        if (imageList.size == 1) {
            LayoutInflater.from(context).inflate(R.layout.round_image_layout, null)
                .apply {
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
                .addSystemView()
        } else {
            imageList.forEach {
                LayoutInflater.from(context).inflate(R.layout.round_image_layout, null)
                    .addSystemView()
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

@Synchronized
fun getRadioAndCache(url: String): Float {
    var bitmap: Bitmap? = null
    return try {
        bitmap = Glide.with(AppContext)
            .asBitmap()
            .load(url)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()
        bitmap.width / bitmap.height.toFloat()
    } catch (e: Exception) {
        0f
    } finally {
        bitmap?.recycle()
    }
}