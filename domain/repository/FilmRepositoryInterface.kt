// domain/repository/FilmRepositoryInterface.kt
package com.example.lazyrow.domain.repository

import com.example.lazyrow.data.model.Film

interface FilmRepositoryInterface {
    suspend fun getPopularFilms(page: Int = 1): List<Film>
    suspend fun getTopRatedFilms(page: Int = 1): List<Film>
    suspend fun getPremieres(year: Int, month: String): List<Film>
}
