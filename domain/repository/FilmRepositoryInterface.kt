
package com.example.lazyrow.domain.repository

import com.example.lazyrow.data.model.Film
import com.example.lazyrow.data.model.FilmDetail

interface FilmRepositoryInterface {
    suspend fun getPopularFilms(page: Int = 1): List<Film>
    suspend fun getTopRatedFilms(page: Int = 1): List<Film>
    suspend fun getPremieres(year: Int, month: String): List<Film>
    suspend fun getFilmDetailById(id: Int): FilmDetail // New method
}
