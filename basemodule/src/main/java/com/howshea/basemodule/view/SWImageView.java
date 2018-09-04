package com.howshea.basemodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.howshea.basemodule.R;


/**
 * Created by Administrator on 2016/7/25.
 * copied from https://github.com/sw950729/SWImageView
 */
public class SWImageView extends AppCompatImageView {

    //save bundle state
    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";
    private static final String STATE_BORDER_WIDTH = "state_border_width";
    private static final String STATE_BORDER_COLOR = "state_border_color";

    private static final int TYPE_NORMAL = -1;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private static final int BORDER_RADIUS_DEFAULT = 10;
    private static final int BORDER_WIDTH = 0;
    private static final int BORDER_COLOR = Color.BLACK;

    private int borderRadius;
    private int type;
    private int border_width;
    private int border_color;
    private Paint paint;
    private Paint border_paint;
    private Matrix matrix;
    private int width;
    private int radius;
    private BitmapShader bitmapShader;
    private RectF rectF;
    private float radio;

    public SWImageView(Context context) {
        super(context);
    }

    public SWImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        matrix = new Matrix();
        paint = new Paint();
        border_paint = new Paint();
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.SWImageView);
        borderRadius = dp2px(array.getDimension(R.styleable.SWImageView_borderRadius, BORDER_RADIUS_DEFAULT));
        type = array.getInt(R.styleable.SWImageView_type, TYPE_NORMAL);
        radio = array.getFloat(R.styleable.SWImageView_radio, 0f);
        border_width = dp2px(array.getDimension(R.styleable.SWImageView_borderWidth, BORDER_WIDTH));
        border_color = array.getInt(R.styleable.SWImageView_borderColor, BORDER_COLOR);
        array.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (type == TYPE_CIRCLE) {
            width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            radio = 1f;
            radius = width / 2 - border_width / 2;
        }
        if (radio != 0f) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width / radio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap == null) {
            return;
        }
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int drawableWidth = bitmap.getWidth();
        int drawableHeight = bitmap.getHeight();
        float dx = 0, dy = 0;

        float scale1 = 1.0f;
        float scale2 = 1.0f;
        final boolean fits = (drawableWidth < 0 || viewWidth == drawableWidth)
                && (drawableHeight < 0 || viewHeight == drawableHeight);
        if (type == TYPE_CIRCLE) {
            int size = Math.min(drawableWidth, drawableHeight);
            scale = width * 1.0f / size;
        } else if (type == TYPE_ROUND) {
            scale = Math.max(viewWidth * 1.0f / drawableWidth, viewHeight
                    * 1.0f / drawableHeight);
        } else {
            return;
        }

        if (drawableWidth <= 0 || drawableHeight <= 0) {
            drawable.setBounds(0, 0, viewWidth, viewHeight);
            matrix = null;
        } else {
            drawable.setBounds(0, 0, drawableWidth, drawableHeight);
            if (ScaleType.MATRIX == getScaleType()) {
                if (matrix.isIdentity()) {
                    matrix = null;
                }
            } else if (fits) {
                matrix = null;
            } else if (ScaleType.CENTER == getScaleType()) {
                matrix.setTranslate(Math.round((viewWidth - drawableWidth) * 0.5f),
                        Math.round((viewHeight - drawableHeight) * 0.5f));
            } else if (ScaleType.CENTER_CROP == getScaleType()) {
                if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
                    dx = (viewWidth - drawableWidth * scale) * 0.5f;
                } else {
                    dy = (viewHeight - drawableHeight * scale) * 0.5f;
                }
                matrix.setScale(scale, scale);
                matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            } else if (ScaleType.CENTER_INSIDE == getScaleType()) {

                if (drawableWidth <= viewWidth && drawableHeight <= viewHeight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) viewWidth / (float) drawableWidth,
                            (float) viewHeight / (float) drawableHeight);
                }
                dx = Math.round((viewWidth - drawableWidth * scale) * 0.5f);
                dy = Math.round((viewHeight - drawableHeight * scale) * 0.5f);
                matrix.setScale(scale, scale);
                matrix.postTranslate(dx, dy);
            } else {
                if (drawableWidth * viewHeight > viewWidth * drawableHeight) {
                    dx = (viewWidth - drawableWidth * scale) * 0.5f;
                } else {
                    dy = (viewHeight - drawableHeight * scale) * 0.5f;
                }
                matrix.setScale(scale, scale);
                matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            }
        }
        if (ScaleType.FIT_XY == getScaleType() && matrix != null) {
            scale1 = viewWidth * 1.0f / drawableWidth;
            scale2 = viewHeight * 1.0f / drawableHeight;
            matrix.setScale(scale1, scale2);
        }
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }

    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        paint.setAntiAlias(true);
        border_paint.setAntiAlias(true);
        border_paint.setStyle(Paint.Style.STROKE);
        border_paint.setColor(border_color);
        border_paint.setStrokeWidth(border_width);
        setBitmapShader();
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(rectF, borderRadius, borderRadius,
                    paint);
            if (border_width > 0) {
                canvas.drawRoundRect(rectF, borderRadius, borderRadius,
                        border_paint);
            }
        } else if (type == TYPE_CIRCLE) {
            canvas.drawCircle(radius, radius, radius, paint);
            if (border_width > 0) {
                canvas.drawCircle(radius, radius, radius - border_width / 2, border_paint);
            }
        } else {
            getDrawable().draw(canvas);
        }
    }

    public void setBorder_width(int border_width) {
        int px = dp2px(border_width);
        if (this.border_width != px) {
            this.border_width = px;
            invalidate();
        }
    }

    public void setBorder_color(int border_color) {
        if (this.border_color == border_color) {
            return;
        }
        this.border_color = border_color;
        border_paint.setColor(border_color);
        invalidate();
    }

    public void setBorderRadius(int borderRadius) {
        int px = dp2px(borderRadius);
        if (this.borderRadius != px) {
            this.borderRadius = px;
            invalidate();
        }
    }

    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_NORMAL;
            }
            requestLayout();
        }
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND) {
            rectF = new RectF(border_width / 2, border_width / 2, getWidth() - border_width / 2, getHeight() - border_width / 2);
        }
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, borderRadius);
        bundle.putInt(STATE_BORDER_WIDTH, border_width);
        bundle.putInt(STATE_BORDER_COLOR, border_color);
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.borderRadius = bundle.getInt(STATE_BORDER_RADIUS);
            this.border_width = bundle.getInt(STATE_BORDER_WIDTH);
            this.border_color = bundle.getInt(STATE_BORDER_COLOR);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
        requestLayout();
    }
}
