package com.howshea.basemodule.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Howshea
 * on 2018/11/1
 */

@Entity(tableName = TABLE_NAME, indices = [Index(value = ["url"], unique = true)])
data class Collection(
    val title: String,
    val url: String,
    //加入收藏的时间
    val time: String,
    var cover: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

const val TABLE_NAME = "collection"