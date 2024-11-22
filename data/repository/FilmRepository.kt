
package com.example.lazyrow.data.repository

import com.example.lazyrow.data.api.ApiService
import com.example.lazyrow.data.model.Film
import com.example.lazyrow.data.model.FilmDetail
import com.example.lazyrow.domain.repository.FilmRepositoryInterface

class FilmRepository(private val apiService: ApiService) : FilmRepositoryInterface {

    override suspend fun getPopularFilms(page: Int): List<Film> {
        val response = apiService.getTopFilms(type = "TOP_POPULAR_ALL", page = page)
        return response.films
    }

    override suspend fun getTopRatedFilms(page: Int): List<Film> {
        val response = apiService.getTopFilms(type = "TOP_250_MOVIES", page = page)
        return response.films
    }

    override suspend fun getPremieres(year: Int, month: String): List<Film> {
        val response = apiService.getPremieres(year, month)
        return response.items
    }

    override suspend fun getFilmDetailById(id: Int): FilmDetail {
        return apiService.getFilmDetail(id)
    }
}
