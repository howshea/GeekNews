package com.howshea.basemodule.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import android.view.View
import android.view.WindowManager

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
        decorView
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
 * 用于浅色状态栏，6.0以下给status bar 设置遮罩
 */
fun <T : Activity> T.setStatusTransAndDarkIcon(@ColorInt statusColor: Int = Color.TRANSPARENT) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // 6+ 实现
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //默认透明色
        window.statusBarColor = statusColor
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 4.4 - 5.0 实现
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
    setDarkStatusIcon(true)
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