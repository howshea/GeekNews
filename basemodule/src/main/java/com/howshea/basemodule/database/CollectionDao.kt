package com.howshea.basemodule.database

import androidx.room.*
import io.reactivex.Flowable
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
    fun checkIsCollected(url: String): Single<Collection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollection(collection: Collection)

    @Query("delete from collection where url = :url")
    fun delete(url: String)
}