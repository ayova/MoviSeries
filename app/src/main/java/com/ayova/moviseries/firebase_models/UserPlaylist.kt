package com.ayova.moviseries.firebase_models

data class UserPlaylist(var id: String? = null,
                        var listName: String? = null,
                        var moviesAdded: ArrayList<Map<String, String>>? = null,
                        var tvShowsAdded: ArrayList<Map<String, String>>? = null)

