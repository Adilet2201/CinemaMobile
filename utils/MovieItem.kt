package com.example.lazyrow.utils

import androidx.annotation.DrawableRes

data class MovieItem(
    val title: String,
    val genres: List<String>,
    val countries: List<String>,
    val rating: String,
    @DrawableRes val image: Int,
    val isPremiere: Boolean
)
