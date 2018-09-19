package com.howshea.home.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Howshea
 * on 2018/7/14.
 */

data class Daily(
    val error: Boolean,
    val results: DailyResults
)

data class DailyResults(
    @SerializedName("Android")
    val android: List<Common>?,
    @SerializedName("iOS")
    val ios: List<Common>?,
    @SerializedName("App")
    val app: List<Common>?,
    @SerializedName("休息视频")
    val video: List<Common>?,
    @SerializedName("拓展资源")
    val sources: List<Common>?,
    @SerializedName("瞎推荐")
    val recommend: List<Common>?,
    @SerializedName("福利")
    val girls: List<Common>?,
    @SerializedName("前端")
    val frontEnd: List<Common>?
)
