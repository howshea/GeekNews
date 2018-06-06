@file:Suppress("NOTHING_TO_INLINE")

package com.howshea.baseutils

import android.app.Activity
import android.os.Build
import android.view.View

/**
 * Created by 陶海啸
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