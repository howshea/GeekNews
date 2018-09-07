package com.howshea.basemodule.extentions

import android.view.View

/**
 * Created by Howshea
 * on 2018/7/17.
 */
var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)