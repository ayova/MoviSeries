package com.ayova.moviseries.models

data class SearchedTVs(
    val page: Int,
    val results: ArrayList<SearchedTV>,
    val total_pages: Int,
    val total_results: Int
)