package com.howshea.basemodule.utils

import android.app.Fragment
import android.content.Context
import android.view.View

/**
 * Created by haipo
 * on 2018/7/17.
 */
//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density + 0.5f).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity + 0.5f).toInt()

fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity + 0.5f).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

//the same for the views
fun View.dip(value: Int): Int = context.dip(value)

fun View.dip(value: Float): Int = context.dip(value)
fun View.sp(value: Int): Int = context.sp(value)
fun View.sp(value: Float): Int = context.sp(value)
fun View.px2dip(px: Int): Float = context.px2dip(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)

//same for Fragments
fun Fragment.dip(value: Int): Int = activity.dip(value)

fun Fragment.dip(value: Float): Int = activity.dip(value)
fun Fragment.sp(value: Int): Int = activity.sp(value)
fun Fragment.sp(value: Float): Int = activity.sp(value)
fun Fragment.px2dip(px: Int): Float = activity.px2dip(px)
fun Fragment.px2sp(px: Int): Float = activity.px2sp(px)

