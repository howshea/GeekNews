package com.howshea.basemodule.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageButton
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.howshea.basemodule.R

/**
 * Created by Howshea
 * on 2018/7/16.
 */
class SimpleToolbar : RelativeLayout {
    private var navButton: ImageButton? = null
    private  var navImage:Drawable?=null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar)
        navImage = attributes?.getDrawable(R.styleable.SimpleToolbar_navigationImage)!!
        attributes.recycle()
        ensureNavButtonView()
        addSystemView(navButton!!)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        navButton?.setOnClickListener {

        }
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
        if (navButton == null && navImage!=null) {
            navButton = AppCompatImageButton(context, null,
                R.attr.toolbarNavigationButtonStyle)
            navButton!!.setImageDrawable(navImage)
            val lp = generateDefaultLayoutParams() as RelativeLayout.LayoutParams
            lp.height = 120
            lp.width =120
            lp.apply {
                addRule(ALIGN_PARENT_START)
                addRule(CENTER_VERTICAL)
            }
            navButton!!.layoutParams = lp
        }
    }
}
