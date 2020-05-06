package com.ayova.moviseries.omdb_api

import com.ayova.moviseries.models.Movie_Genres
import com.ayova.moviseries.models.TV_Genres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("genre/movie/list")
    fun getMovieGenres(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<Movie_Genres>

    @GET("genre/tv/list")
    fun getTVGenres(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<TV_Genres>
}