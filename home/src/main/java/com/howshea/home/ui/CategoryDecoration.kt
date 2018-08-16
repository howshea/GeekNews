package com.howshea.home.ui

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.howshea.basemodule.AppContext
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.utils.sp
import com.howshea.home.model.Common

/**
 * Created by Howshea
 * on 2018/8/16
 */
class CategoryDecoration(val data: List<Common>, context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        textSize = titleSize.toFloat()
        isAntiAlias = true
    }
    private val bounds = Rect()
    private var titleHeight = AppContext.dp(30)
    private var titleSize = AppContext.sp(16)
    private val inflater = LayoutInflater.from(context)

}