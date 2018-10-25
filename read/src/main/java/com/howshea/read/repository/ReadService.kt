package com.howshea.read.repository

import com.howshea.basemodule.web.retrofit
import com.howshea.read.model.Feed
import com.howshea.read.model.MainCategory
import com.howshea.read.model.SubCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Howshea
 * on 2018/10/25
 */
interface ReadApi {
    @GET("xiandu/categories")
    fun getMainCategory(): Observable<MainCategory>

    @GET("xiandu/category/{enName}")
    fun getSubCategory(@Path("enName") enName: String): Observable<SubCategory>

    @GET("xiandu/data/id/{subCategory}/count/{count}/page/{page}")
    fun getFeed(
        @Path("subCategory") subCategory: String,
        @Path("count") count: Int,
        @Path("page") page: Int
    ): Observable<Feed>
}

object ReadService : ReadApi by retrofit.create(ReadApi::class.java)