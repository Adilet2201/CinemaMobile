package com.example.lazyrow.data.model

import com.google.gson.annotations.SerializedName

data class FilmDetail(
    @SerializedName("kinopoiskId") val kinopoiskId: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String?,
    @SerializedName("posterUrl") val posterUrl: String?,
    @SerializedName("posterUrlPreview") val posterUrlPreview: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("filmLength") val filmLength: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("countries") val countries: List<Country>,
    @SerializedName("genres") val genres: List<Genre>
)
