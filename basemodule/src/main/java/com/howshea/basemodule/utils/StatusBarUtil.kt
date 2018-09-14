package com.howshea.basemodule.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.howshea.basemodule.extentions.topPadding

/**
 * Created by Howshea
 * on 2018/6/6.
 */


/**
 * 状态栏反色
 */
fun <T : Activity> T.setDarkStatusIcon(dark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decorView = window.decorView
        decorView ?: return
        var vis = decorView.systemUiVisibility
        vis = if (dark) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }
}

/**
 * 透明状态栏
 */
fun <T : Activity> T.setStatusTransparent() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // 5.0+ 实现
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 4.4 实现
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

/**
 * 获取状态栏高度
 * @return px
 */
fun <T : Context> T.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val res = this.resources
    val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = res.getDimensionPixelSize(resourceId)
    }
    return statusBarHeight
}

fun <T : Context> T.setUnderApi23StatusBarShade(view: View) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        view.topPadding = this.getStatusBarHeight()
    }
}