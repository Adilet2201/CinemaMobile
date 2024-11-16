// data/model/FilmCollectionResponse.kt
package com.example.lazyrow.data.model

import com.google.gson.annotations.SerializedName

data class FilmCollectionResponse(
    @SerializedName("pagesCount") val pagesCount: Int,
    @SerializedName("films") val films: List<Film>
)
