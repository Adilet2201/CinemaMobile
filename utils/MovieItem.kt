// utils/MovieItem.kt
package com.example.lazyrow.utils

import androidx.annotation.DrawableRes

data class MovieItem(
    val title: String,
    val genres: List<String>,
    val countries: List<String>,
    val rating: String,
    @DrawableRes val image: Int,
    val isPremiere: Boolean = false,
    val isPopular: Boolean = false,
    val isTop250: Boolean = false
)
