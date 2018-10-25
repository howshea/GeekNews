package com.howshea.read.model

/**
 * Created by howshea on 2018-10-25
 */
data class SubCategory(var error: Boolean,
                       var results: List<Results>) {
    data class Results(var icon: String,
                       var id: String,
                       var title: String)
}