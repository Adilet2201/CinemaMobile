
package com.example.lazyrow.data.api

import com.example.lazyrow.data.model.FilmCollectionResponse
import com.example.lazyrow.data.model.FilmDetail
import com.example.lazyrow.data.model.PremiereResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("X-API-KEY: d4b992f1-a288-40d1-a419-06cdb614d381")
    @GET("api/v2.2/films/collections")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int = 1
    ): FilmCollectionResponse

    @Headers("X-API-KEY: d4b992f1-a288-40d1-a419-06cdb614d381")
    @GET("api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): PremiereResponse

    @Headers("X-API-KEY: d4b992f1-a288-40d1-a419-06cdb614d381")
    @GET("api/v2.2/films/{filmId}")
    suspend fun getFilmDetail(
        @Path("filmId") filmId: Int
    ): FilmDetail
}
