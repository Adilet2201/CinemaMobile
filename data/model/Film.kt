
package com.example.lazyrow.data.model

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("kinopoiskId") val id: Int,
    @SerializedName("nameRu") val title: String?,
    @SerializedName("nameEn") val titleEn: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("posterUrlPreview") val posterUrlPreview: String?,
    @SerializedName("posterUrl") val posterUrl: String?,
    @SerializedName("description") val description: String?
)
