package com.howshea.basemodule.extentions

import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Created by haipo
 * on 2018/7/17.
 */
inline val Activity.contentView: View?
    get() = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)