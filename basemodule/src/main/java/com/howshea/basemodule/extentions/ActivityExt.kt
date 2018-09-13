package com.howshea.basemodule.extentions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.howshea.basemodule.utils.toast

/**
 * Created by Howshea
 * on 2018/7/17.
 */
inline val Activity.contentView: View?
    get() = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)


/**
 * 粘贴到系统剪贴板
 */
fun Context.copyToClipBoard(url: String) {
    val cm = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val text = ClipData.newPlainText("url", url)
    cm.primaryClip = text
    toast("已复制到剪贴板")
}