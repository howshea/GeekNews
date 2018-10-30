package com.howshea.basemodule.extentions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import com.howshea.basemodule.utils.toast

/**
 * Created by Howshea
 * on 2018/7/17.
 */

/**
 * 粘贴到系统剪贴板
 */
fun Context.copyToClipBoard(url: String) {
    val cm = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val text = ClipData.newPlainText("url", url)
    cm.primaryClip = text
    toast("已复制到剪贴板")
}

fun Context.getVersionName(): String {
    return try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }

}