package com.howshea.read.model

/**
 * Created by howshea on 2018-10-25
 */
data class MainCategory(var error: Boolean,
                        var results: List<Results>) {

    data class Results(var en_name: String,
                       var name: String,
                       var rank: Int)
}