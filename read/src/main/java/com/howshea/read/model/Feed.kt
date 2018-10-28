package com.howshea.read.model

/**
 * Created by howshea on 2018-10-25
 */
data class Feed(var error: Boolean,
                var results: List<Results>) {
    data class Results(var content: String,
                       var cover: String?,
                       var crawled: Long,
                       var created_at: String,
                       var deleted: Boolean,
                       var published_at: String,
                       var site: Site,
                       var title: String,
                       var url: String) {
        data class Site(var cat_cn: String,
                        var cat_en: String,
                        var desc: String,
                        var icon: String,
                        var id: String,
                        var name: String,
                        var url: String)
    }
}