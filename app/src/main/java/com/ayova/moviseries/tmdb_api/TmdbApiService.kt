package com.ayova.moviseries.tmdb_api

import com.ayova.moviseries.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    /**
     * Gets a list of all movie genres
     */
    @GET("genre/movie/list")
    fun getMovieGenres(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<Movie_Genres>

    /**
     * Gets a list of all TV shows genres
     */
    @GET("genre/tv/list")
    fun getTVGenres(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<TV_Genres>

    /**
     * Gets a list 20 movies by popularity
     */
    @GET("discover/movie")
    fun discoverMovies(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<DiscoverMovies>

    /**
     * Gets a list 20 tv shows by popularity
     */
    @GET("discover/tv")
    fun discoverTV(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<DiscoverTV>

    /**
     * Search for movies with the given query in the title
     * @param query is the name of the movie it's searching for
     */
    @GET("search/movie")
    fun searchMovie(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE, @Query("query") query: String): Call<SearchedMovies>

    /**
     * Search for movies with the given query in the title
     * @param query is the name of the tv show it's searching for
     */
    @GET("search/tv")
    fun searchTV(@Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE, @Query("query") query: String): Call<SearchedTVs>

    /**
     * Search for a movie's details (by id)
     * @param movie_id is the unique identifier for the given movie
     */
    @GET("movie/{movie_id}")
    fun findMovieById(@Path("movie_id") movie_id: String, @Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<MovieDetails>

    /**
     * Search for a tv show's details (by id)
     * @param tv_id is the unique identifier for the given tv show
     */
    @GET("tv/{tv_id}")
    fun findTVShowById(@Path("tv_id") tv_id: String, @Query("api_key") apikey: String = TmdbApi.API_KEY, @Query("language") language:
    String = TmdbApi.LANGUAGE): Call<TVShowDetails>

}