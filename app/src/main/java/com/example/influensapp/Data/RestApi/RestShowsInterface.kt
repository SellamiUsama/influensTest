package com.example.influensapp.Data.RestApi

import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Entities.PopularShows.PopularShows
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestShowsInterface
{
    @GET("tv/popular")
    fun getPopularShows(@Query("page")page:Int) : Single<PopularShows>

    @GET("tv/{id}")
    fun getShow(@Path("id") id:Int): Single<Show>

}