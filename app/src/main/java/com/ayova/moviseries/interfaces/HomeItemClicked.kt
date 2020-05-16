package com.ayova.moviseries.interfaces

import android.content.Context

interface HomeItemClicked {
    fun onHomeItemClick(context: Context, position: Int)
}