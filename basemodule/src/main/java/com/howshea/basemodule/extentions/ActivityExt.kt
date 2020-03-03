package com.howshea.basemodule.extentions

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup

/**
 * Created by Howshea
 * on 2018/10/18
 */

fun AppCompatActivity.addFragment(layoutRes: Int, otherFragment: Fragment) {
    val fm = supportFragmentManager
    fm.beginTransaction()
        .add(layoutRes, otherFragment)
        .commit()
}

inline val Activity.contentView: View?
    get() = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)