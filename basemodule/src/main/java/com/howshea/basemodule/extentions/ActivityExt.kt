package com.howshea.basemodule.extentions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

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