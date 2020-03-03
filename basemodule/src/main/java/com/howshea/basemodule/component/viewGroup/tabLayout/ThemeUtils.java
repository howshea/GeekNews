package com.howshea.basemodule.component.viewGroup.tabLayout;
import android.content.Context;
import android.content.res.TypedArray;
/**
 * Created by Howshea
 * on 2018/10/24
 */
class ThemeUtils {

    private static final int[] APPCOMPAT_CHECK_ATTRS = {
            androidx.appcompat.R.attr.colorPrimary
    };

    static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        a.recycle();
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
