package com.example.lazyrow.repository

import com.example.lazyrow.model.Film
import com.example.lazyrow.network.RetrofitInstance

class FilmRepository {

    private val apiService = RetrofitInstance.apiService

    suspend fun getPopularFilms(page: Int = 1): List<Film> {
        val response = apiService.getTopFilms(type = "TOP_100_POPULAR_FILMS", page = page)
        return response.films
    }

    suspend fun getTopRatedFilms(page: Int = 1): List<Film> {
        val response = apiService.getTopFilms(type = "TOP_250_BEST_FILMS", page = page)
        return response.films
    }

    suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = apiService.getPremieres(year = year, month = month)
        return response.items
    }
}
