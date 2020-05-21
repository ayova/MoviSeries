package com.ayova.moviseries.firebase_models

data class UserPlaylist(var listName: String? = null,
                        var moviesAdded: ArrayList<String>? = null,
                        var tvShowsAdded: ArrayList<String>? = null)