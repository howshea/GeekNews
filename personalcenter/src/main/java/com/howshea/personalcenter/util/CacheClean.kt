package com.howshea.personalcenter.util

import android.content.Context
import android.os.Environment
import com.howshea.basemodule.extentions.deleteDir
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

