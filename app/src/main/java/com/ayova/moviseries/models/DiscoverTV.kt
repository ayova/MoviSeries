package com.ayova.moviseries.models

data class DiscoverTV(
    val page: Int,
    val results: ArrayList<DiscoveredTV>,
    val total_pages: Int,
    val total_results: Int
)