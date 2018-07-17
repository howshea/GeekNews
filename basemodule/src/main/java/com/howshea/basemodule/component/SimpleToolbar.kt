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
import com.howshea.basemodule.utils.dip

/**
 * Created by Howshea
 * on 2018/7/16.
 */


class SimpleToolbar : FrameLayout {
    private var navButton: ImageButton? = null
    private var navigationDrawable: Drawable? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar)?.apply {
            if (hasValue(R.styleable.SimpleToolbar_navIcon))
                navigationDrawable = getDrawable(R.styleable.SimpleToolbar_navIcon)
            recycle()
        }
        initSubView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        navButton?.setOnClickListener { }
    }

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
        if (navButton == null && navigationDrawable != null) {
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

    private val navButtonLp:FrameLayout.LayoutParams by lazy {
        (generateDefaultLayoutParams() as FrameLayout.LayoutParams).apply {
            height = dip(24)
            width = dip(24)
            topMargin = dip(12)
            bottomMargin = dip(12)
            marginStart = dip(10)
            gravity = Gravity.START
        }
    }

}
