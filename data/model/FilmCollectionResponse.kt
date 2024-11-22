
package com.example.lazyrow.data.model

import com.google.gson.annotations.SerializedName

data class FilmCollectionResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("items") val films: List<Film>
)
