package com.howshea.basemodule.component.viewGroup.nineGrid


import android.graphics.*
import android.os.Build
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Preconditions
import com.bumptech.glide.util.Synthetic
import com.bumptech.glide.util.Util
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by Howshea
 * on 2018/9/7
 *
 * A [BitmapTransformation] which rounds the corners of a bitmap.
 */
class RoundedCorners(private val roundingRadius: Int, val borderWidth: Int) : BitmapTransformation() {

    private val ID = "com.bumptech.glide.load.resource.bitmap.RoundedCorners"
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)

    init {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.")
    }

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return roundedCorners(pool, toTransform, roundingRadius, borderWidth)
    }

    override fun equals(o: Any?): Boolean {
        if (o is RoundedCorners) {
            val other = o as RoundedCorners?
            return roundingRadius == other!!.roundingRadius
        }
        return false
    }

    override fun hashCode(): Int {
        return Util.hashCode(ID.hashCode(),
            Util.hashCode(roundingRadius))
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)

        val radiusData = ByteBuffer.allocate(4).putInt(roundingRadius).array()
        messageDigest.update(radiusData)
    }


}

fun roundedCorners(
    pool: BitmapPool, inBitmap: Bitmap, roundingRadius: Int, borderWidth: Int): Bitmap {
    Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.")

    // Alpha is required for this transformation.
    val toTransform = getAlphaSafeBitmap(pool, inBitmap)
    val result = pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)

    result.setHasAlpha(true)

    val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP,
        Shader.TileMode.CLAMP)
    val paint = Paint().apply {
        isAntiAlias = true
        this.shader = shader
    }
    val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = borderWidth.toFloat()
        color = Color.parseColor("#DBDBDB")
        isAntiAlias = true
    }
    val rect = RectF(borderWidth.toFloat(), borderWidth.toFloat(), result.width - borderWidth.toFloat(), result.height - borderWidth.toFloat())
    val borderRect = RectF(borderWidth / 2f, borderWidth / 2f, result.width - borderWidth / 2f, result.height - borderWidth / 2f)
    BITMAP_DRAWABLE_LOCK.lock()
    try {
        val canvas = Canvas(result)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.drawRoundRect(rect, roundingRadius.toFloat()*0.9f, roundingRadius.toFloat()*0.9f, paint)
        canvas.drawRoundRect(borderRect, roundingRadius.toFloat(), roundingRadius.toFloat(), borderPaint)
        canvas.setBitmap(null)
    } finally {
        BITMAP_DRAWABLE_LOCK.unlock()
    }

    if (toTransform != inBitmap) {
        pool.put(toTransform)
    }

    return result
}

private fun getAlphaSafeBitmap(pool: BitmapPool,
                               maybeAlphaSafe: Bitmap): Bitmap {
    if (Bitmap.Config.ARGB_8888 == maybeAlphaSafe.config) {
        return maybeAlphaSafe
    }

    val argbBitmap = pool.get(maybeAlphaSafe.width, maybeAlphaSafe.height,
        Bitmap.Config.ARGB_8888)
    Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0f /*left*/, 0f /*top*/, null /*paint*/)

    // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
    // when we're finished with it.
    return argbBitmap
}


private val MODELS_REQUIRING_BITMAP_LOCK = HashSet(
    Arrays.asList(
        // Moto X gen 2
        "XT1085",
        "XT1092",
        "XT1093",
        "XT1094",
        "XT1095",
        "XT1096",
        "XT1097",
        "XT1098",
        // Moto G gen 1
        "XT1031",
        "XT1028",
        "XT937C",
        "XT1032",
        "XT1008",
        "XT1033",
        "XT1035",
        "XT1034",
        "XT939G",
        "XT1039",
        "XT1040",
        "XT1042",
        "XT1045",
        // Moto G gen 2
        "XT1063",
        "XT1064",
        "XT1068",
        "XT1069",
        "XT1072",
        "XT1077",
        "XT1078",
        "XT1079"
    )
)

private val BITMAP_DRAWABLE_LOCK = if (MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL))
    ReentrantLock()
else
    NoLock()

private class NoLock @Synthetic
internal constructor() : Lock {
    override fun lock() {
        // do nothing
    }

    @Throws(InterruptedException::class)
    override fun lockInterruptibly() {
        // do nothing
    }

    override fun tryLock(): Boolean {
        return true
    }

    @Throws(InterruptedException::class)
    override fun tryLock(time: Long, unit: TimeUnit): Boolean {
        return true
    }

    override fun unlock() {
        // do nothing
    }

    override fun newCondition(): Condition {
        throw UnsupportedOperationException("Should not be called")
    }
}
