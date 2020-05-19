package com.ayova.moviseries.tmdb_models

data class SearchedMovies(
    val page: Int,
    val results: ArrayList<SearchedMovie>,
    val total_pages: Int,
    val total_results: Int
)