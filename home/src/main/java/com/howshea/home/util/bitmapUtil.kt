package com.howshea.home.util

import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.howshea.basemodule.AppContext

/**
 * Created by Howshea
 * on 2018/9/8.
 */
@Synchronized
fun getRadioAndCache(url: String): Float {
    var bitmap: Bitmap? = null
    return try {
        bitmap = Glide.with(AppContext)
            .asBitmap()
            .load(url)
            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()
        bitmap.width / bitmap.height.toFloat()
    } catch (e: Exception) {
        0f
    } finally {
        bitmap?.recycle()
    }
}