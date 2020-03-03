package com.howshea.basemodule.component.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.LruCache
import android.view.Gravity
import com.howshea.basemodule.R

/**
 * Created by Howshea
 * on 2018/9/18
 */
class ShadowImageView : AppCompatImageView {
    private var shadowAlpha = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.ShadowImageView)
        attributes?.let {
            shadowAlpha = it.getInt(R.styleable.ShadowImageView_shadowAlpha, 0x10)
        }
        attributes?.recycle()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = makeCubicGradientScrimDrawable(Gravity.TOP, shadowAlpha)
    }
}


private val cubicGradientScrimCache = LruCache<Int, Drawable>(10)

fun Float.constrain(min: Float, max: Float): Float = Math.max(min, Math.min(max, this))

/**
 * Creates an approximated cubic gradient using a multi-stop linear gradient. See
 * [this post](https://plus.google.com/+RomanNurik/posts/2QvHVFWrHZf) for more
 * details.
 */
@SuppressLint("RtlHardcoded")
@JvmOverloads
fun makeCubicGradientScrimDrawable(
    gravity: Int,
    alpha: Int = 0xFF,
    red: Int = 0x0,
    green: Int = 0x0,
    blue: Int = 0x0,
    requestedStops: Int = 8
): Drawable {
    var numStops = requestedStops

    // Generate a cache key by hashing together the inputs, based on the method described in the Effective Java book
    var cacheKeyHash = Color.argb(alpha, red, green, blue)
    cacheKeyHash = 31 * cacheKeyHash + numStops
    cacheKeyHash = 31 * cacheKeyHash + gravity

    val cachedGradient = cubicGradientScrimCache.get(cacheKeyHash)
    if (cachedGradient != null) {
        return cachedGradient
    }

    numStops = Math.max(numStops, 2)

    val paintDrawable = PaintDrawable().apply {
        shape = RectShape()
    }

    val stopColors = IntArray(numStops)

    for (i in 0 until numStops) {
        val x = i * 1f / (numStops - 1)
        val opacity = Math.pow(x.toDouble(), 3.0).toFloat().constrain(0f, 1f)
        stopColors[i] = Color.argb((alpha * opacity).toInt(), red, green, blue)
    }

    val x0: Float
    val x1: Float
    val y0: Float
    val y1: Float
    when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
        Gravity.LEFT -> {
            x0 = 1f
            x1 = 0f
        }
        Gravity.RIGHT -> {
            x0 = 0f
            x1 = 1f
        }
        else -> {
            x0 = 0f
            x1 = 0f
        }
    }
    when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
        Gravity.TOP -> {
            y0 = 1f
            y1 = 0f
        }
        Gravity.BOTTOM -> {
            y0 = 0f
            y1 = 1f
        }
        else -> {
            y0 = 0f
            y1 = 0f
        }
    }

    paintDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return LinearGradient(
                width * x0,
                height * y0,
                width * x1,
                height * y1,
                stopColors, null,
                Shader.TileMode.CLAMP)
        }
    }

    cubicGradientScrimCache.put(cacheKeyHash, paintDrawable)
    return paintDrawable
}
