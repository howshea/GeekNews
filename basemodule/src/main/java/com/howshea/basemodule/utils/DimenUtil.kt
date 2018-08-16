package com.howshea.basemodule.utils

import android.app.Fragment
import android.content.Context
import android.view.View

/**
 * Created by haipo
 * on 2018/7/17.
 */
//returns dp(dp) dimension value in pixels
fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()

fun Context.dp(value: Float): Int = (value * resources.displayMetrics.density + 0.5f).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity + 0.5f).toInt()

//converts px value into dp or sp
fun Context.px2dp(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

//the same for the views
fun View.dp(value: Int): Int = context.dp(value)

fun View.dp(value: Float): Int = context.dp(value)
fun View.sp(value: Int): Int = context.sp(value)
fun View.sp(value: Float): Int = context.sp(value)
fun View.px2dp(px: Int): Float = context.px2dp(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)

//same for Fragments
fun Fragment.dp(value: Int): Int = activity.dp(value)

fun Fragment.dp(value: Float): Int = activity.dp(value)
fun Fragment.sp(value: Int): Int = activity.sp(value)
fun Fragment.sp(value: Float): Int = activity.sp(value)
fun Fragment.px2dp(px: Int): Float = activity.px2dp(px)
fun Fragment.px2sp(px: Int): Float = activity.px2sp(px)

