package com.example.influensapp.Data.RestApi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "f6b11427f11d3fe225b002bac0104fbb"
const val SERVER_URL = "https://api.themoviedb.org/3/"
const val SERVER_IMAGES_URL = "https://image.tmdb.org/t/p/w400/"
const val INITIAL_PAGE = 1
const val SHOWS_PER_PAGE = 20

object RestShowsClient {

    fun getClient():RestShowsInterface{
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60 , TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(SERVER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestShowsInterface::class.java)


    }
}