package com.howshea.basemodule.component.viewGroup

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Howshea
 * on 2018/7/18.
 */
class NoScrollViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}