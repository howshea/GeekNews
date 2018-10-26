package com.howshea.read.repository

import com.howshea.basemodule.web.retrofit
import com.howshea.read.model.Feed
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Howshea
 * on 2018/10/25
 */
interface ReadApi {

    @GET("xiandu/data/id/{typeId}/count/{count}/page/{page}")
    fun getFeed(
        @Path("typeId") typeId: String,
        @Path("count") count: Int,
        @Path("page") page: Int
    ): Observable<Feed>
}

object ReadService : ReadApi by retrofit.create(ReadApi::class.java)