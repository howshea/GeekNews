package com.howshea.basemodule.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * Created by Howshea
 * on 2018/11/1
 */

@Database(entities = [Collection::class], version = 1)
abstract class CollectionDataBase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

    companion object {
        private const val DB_NAME = "geekNews.db"

        @Volatile
        private var INSTANCE: CollectionDataBase? = null

        fun getInstance(context: Context): CollectionDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create(context).also { INSTANCE = it }
            }

        private fun create(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CollectionDataBase::class.java,
                DB_NAME)
                .build()
    }
}