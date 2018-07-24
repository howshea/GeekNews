package com.howshea.basemodule.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
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

@SuppressLint("ShowToast")
fun <T:Fragment> T.toast(text: CharSequence){
    toast ?: let {
        toast = Toast.makeText(this.context, null, Toast.LENGTH_SHORT)
    }
    toast?.apply {
        setText(text)
        show()
    }
}