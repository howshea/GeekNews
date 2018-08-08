package com.howshea.home.repository

import com.howshea.basemodule.web.retrofit
import com.howshea.home.model.Daily
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Howshea
 * on 2018/7/14.
 */
interface HomeApi {

    @GET("day/{year}/{month}/{day}")
    fun getDaily(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Observable<Daily>

    @GET("today")
    fun getToday(): Observable<Daily>
}

object HomeService : HomeApi by retrofit.create(HomeApi::class.java)