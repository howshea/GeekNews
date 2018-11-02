package com.howshea.data.model

/**
 * Created by Howshea on 2018-10-29
 */
data class Data(var error: Boolean,
                var results: List<Results>) {
    data class Results(var desc: String,
                       var publishedAt: String,
                       var url: String,
                       var used: Boolean,
                       var who: String)
}