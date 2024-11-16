// data/model/Genre.kt
package com.example.lazyrow.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("genre") val genre: String
)
