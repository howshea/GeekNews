package com.howshea.home.model

data class Common(
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String,
    var images: List<String>?,
    @Transient
    var radio: Float
)