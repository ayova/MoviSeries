package com.ayova.moviseries.tmdb_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApi {
    lateinit var service: TmdbApiService
    val URL = "https://api.themoviedb.org/3/"
    val POSTER_URL = "https://image.tmdb.org/t/p/w500/"
    val LANGUAGE = "en-US"
    val API_KEY = "d8fd16e131618860a80cb75f720d694c"

    fun initService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(TmdbApiService::class.java)
    }
}