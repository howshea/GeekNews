package com.howshea.basemodule.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by Howshea
 * on 2018/11/1
 */
@Dao
interface CollectionDao {
    @Query("SELECT * FROM collection ORDER BY id DESC")
    fun getCollections(): Flowable<List<Collection>>

    @Query("SELECT * FROM collection WHERE url = :url")
    fun checkIsCollected(url: String):Single<Collection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollection(collection: Collection)

    @Delete()
    fun delete(collection: Collection)
}