package com.howshea.home.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Howshea
 * on 2018/9/19
 */

data class History(
    val error: Boolean,
    val results: List<HistoryResult>
)

data class HistoryResult(
    @SerializedName("content")
    var cover: String?,
    val publishedAt: String,
    val title: String
)