package com.howshea.basemodule.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Howshea
 * on 2018/11/1
 */

@Entity(tableName = TABLE_NAME)
data class Collection(
    val title: String,
    val url: String,
    //加入收藏的时间
    val time: String,
    var cover: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

const val TABLE_NAME = "collection"