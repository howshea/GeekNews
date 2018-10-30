package com.howshea.personalcenter.util

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by Howshea
 * on 2018/10/30
 */
fun Context.clearCache(): Boolean {
    var result = cacheDir.deleteDir()
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        result = result and externalCacheDir.deleteDir()
    }
    return result
}

private fun File.deleteDir(): Boolean {
    if (isDirectory) {
        val children = list()
        if (children != null && children.isNotEmpty()) {
            children.forEach {
                val success = File(this, it).deleteDir()
                if (!success) return false
            }
        } else {
            return delete()
        }
    } else if (exists()) {
        return delete()
    }
    return true
}