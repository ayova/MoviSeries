package com.ayova.moviseries.tmdb_models

data class DiscoverMovies(
    val page: Int,
    val results: ArrayList<DiscoveredMovie>,
    val total_pages: Int,
    val total_results: Int
)