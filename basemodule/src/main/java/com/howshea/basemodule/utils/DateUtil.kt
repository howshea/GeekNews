package com.howshea.basemodule.utils

import java.util.*

/**
 * Created by haipo
 * on 2018/7/16.
 */
fun getYear(): Int {
    return Calendar.getInstance().get(Calendar.YEAR)
}

fun getMonth(): Int {
    return Calendar.getInstance().get(Calendar.MONTH)+1
}

fun getDay():Int{
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}