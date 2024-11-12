package com.example.lazyrow.model

import com.google.gson.annotations.SerializedName

data class PremiereResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<Film>
)
