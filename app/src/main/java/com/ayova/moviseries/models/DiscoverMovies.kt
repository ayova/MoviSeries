package com.ayova.moviseries.models

data class DiscoverMovies(
    val page: Int,
    val results: ArrayList<DiscoveredMovie>,
    val total_pages: Int,
    val total_results: Int
)