package com.howshea.data.repository

import com.howshea.basemodule.web.retrofit
import com.howshea.data.model.Data
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Howshea
 * on 2018/10/29.
 */
interface DataApi {
    @GET("data/{type}/{count}/{page}")
    fun getDataOfType(
        @Path("type") type: String,
        @Path("count") count: Int,
        @Path("page") page: Int
    ): Observable<Data>
}


object DataServices : DataApi by retrofit.create(DataApi::class.java)