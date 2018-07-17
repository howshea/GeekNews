package com.howshea.basemodule.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageButton
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import com.howshea.basemodule.R
import com.howshea.basemodule.extentions.invoke
import com.howshea.basemodule.utils.dip

/**
 * Created by Howshea
 * on 2018/7/16.
 */
/**
 * @attr R.styleable.SimpleToolbar_navIcon
 * @attr R.styleable.SimpleToolbar_menuIcon
 * @attr R.styleable.SimpleToolbar_contentHeight
 * @attr R.styleable.SimpleToolbar_title
 * @attr R.styleable.SimpleToolbar_titleStyle
 * @attr R.styleable.SimpleToolbar_titleSize
 * @attr R.styleable.SimpleToolbar_titleColor
 */

class SimpleToolbar : FrameLayout {
    private var navButton: ImageButton? = null
    private var navigationDrawable: Drawable? = null
    private val iconSize = dip(24)
    //默认高度48dp
    private var contentHeight = dip(48)
    private val iconTopBottomMargin
        get() = (contentHeight - iconSize) / 2

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context
            ?.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar)
            ?.apply {
                if (hasValue(R.styleable.SimpleToolbar_navIcon))
                    navigationDrawable = getDrawable(R.styleable.SimpleToolbar_navIcon)
                if (hasValue(R.styleable.SimpleToolbar_contentHeight)) {
                    contentHeight = getDimension(R.styleable.SimpleToolbar_contentHeight, 0f).toInt()
                    (contentHeight < iconSize) {
                        throw IllegalArgumentException("contentHeight must be greater than iconSize")
                    }
                }
                recycle()
            }
        initSubView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun initSubView() {
        ensureNavButtonView()
    }

    private fun addSystemView(v: View) {
        val vlp = v.layoutParams
        val lp: LayoutParams
        lp = if (vlp == null) {
            generateDefaultLayoutParams() as LayoutParams
        } else if (!checkLayoutParams(vlp)) {
            generateLayoutParams(vlp) as LayoutParams
        } else {
            vlp as LayoutParams
        }
        addView(v, lp)
    }

    private fun ensureNavButtonView() {
        (navButton == null && navigationDrawable != null) {
            navButton = AppCompatImageButton(context, null,
                R.attr.toolbarNavigationButtonStyle).apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
                setImageDrawable(navigationDrawable)
            }.apply {
                layoutParams = navButtonLp
                addSystemView(this)
            }
        }
    }

    private val navButtonLp: FrameLayout.LayoutParams by lazy {
        (generateDefaultLayoutParams() as FrameLayout.LayoutParams).apply {
            height = iconSize
            width = iconSize
            topMargin = iconTopBottomMargin
            bottomMargin = iconTopBottomMargin
            marginStart = dip(12)
            gravity = Gravity.START
        }
    }

    fun setNavOnClick(click: (v: View) -> Unit) {
        navButton?.setOnClickListener { click(it) }
    }

}
