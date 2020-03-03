package com.howshea.basemodule.component.viewGroup.tabLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import com.google.android.material.R;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Howshea
 * on 2018/10/24
 */
public final class TabItem extends View {
    final CharSequence mText;
    final Drawable mIcon;
    final int mCustomLayout;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);


        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TabItem);
        mText = a.getText(R.styleable.TabItem_android_text);
        mIcon = a.getDrawable(R.styleable.TabItem_android_icon);
        mCustomLayout = a.getResourceId(R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
