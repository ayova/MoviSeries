package com.ayova.moviseries.tmdb_models

/**
 * @param name is the name of the list to be displayed
 * @param itemsList is the variable that will hold a list
 *        of DiscoveredMovie or DiscoveredTV items
 */
data class HomeList(
    var name: String,
    var itemsList: Any)
//    var tvList: ArrayList<DiscoveredTV>? = null,
//    var movieList: ArrayList<DiscoveredMovie>? = null)