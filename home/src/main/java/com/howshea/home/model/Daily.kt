package com.howshea.home.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Howshea
 * on 2018/7/14.
 */

data class Daily(
    val category: List<String>,
    val error: Boolean,
    val results: Results
)

data class Results(
    val android: List<Common>?,
    val ios: List<Common>?,
    @SerializedName("休息视频")
    val video: List<Common>?,
    @SerializedName("拓展资源")
    val sources: List<Common>?,
    @SerializedName("瞎推荐")
    val recommend: List<Common>?,
    @SerializedName("福利")
    val girls: List<Common>?
)


data class Common(
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String,
    var images: List<String>
)

