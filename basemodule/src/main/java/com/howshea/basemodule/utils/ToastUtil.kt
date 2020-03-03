package com.howshea.basemodule.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.widget.Toast

/**
 * Created by Howshea
 * on 2018/7/24.
 */
private var toast: Toast? = null

@SuppressLint("ShowToast")
fun Context.toast(text: CharSequence) {
    toast ?: let {
        toast = Toast.makeText(this, null, Toast.LENGTH_SHORT)
    }
    toast?.apply {
        setText(text)
        show()
    }
}

/**
 * @param resId 字符串资源
 */
fun Context.toast(@StringRes resId:Int){
    toast(getString(resId))
}

@SuppressLint("ShowToast")
fun <T: Fragment> T.toast(text: CharSequence){
    context?.toast(text)
}

/**
 * @param resId 字符串资源
 */
fun <T: Fragment> T.toast(@StringRes resId: Int){
    toast(getString(resId))
}