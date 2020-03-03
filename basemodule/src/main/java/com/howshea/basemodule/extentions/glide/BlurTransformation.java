package com.howshea.basemodule.extentions.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;


public class BlurTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID =
            "com.howshea.glide.transformations.BlurTransformation." + VERSION;

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private int radius;
    private float scale;

    public BlurTransformation() {
        this(MAX_RADIUS, 0.1f);
    }

    public BlurTransformation(int radius) {
        this(radius, 0.1f);
    }

    public BlurTransformation(int radius, float scale) {
        this.radius = radius;
        this.scale = scale;
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = ((int) (width * scale));
        int scaledHeight = ((int) (height * scale));

        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
//        Bitmap bitmap = pool.get(100, 100, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 * scale, 1 * scale);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        bitmap = Blur.blur(bitmap, radius);

        return bitmap;
    }

    @Override
    public String toString() {
        return "BlurTransformation(radius=" + radius + ", scale=" + scale + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation &&
                ((BlurTransformation) o).radius == radius &&
                ((BlurTransformation) o).scale == scale;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + radius * 1000 + (int) scale * 10;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + radius + scale).getBytes(CHARSET));
    }
}
