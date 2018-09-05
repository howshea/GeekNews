package com.howshea.basemodule

import android.app.Application
import android.content.ContextWrapper
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

/**
 * Created by Howshea
 * on 2018/7/13.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        @JvmStatic
        lateinit var INSTANCE: App
    }
}

object AppContext : ContextWrapper(App.INSTANCE)


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