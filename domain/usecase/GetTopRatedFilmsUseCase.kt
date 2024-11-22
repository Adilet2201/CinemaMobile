package com.example.lazyrow.domain.usecase

import com.example.lazyrow.data.model.Film
import com.example.lazyrow.domain.repository.FilmRepositoryInterface

class GetTopRatedFilmsUseCase(private val repository: FilmRepositoryInterface) {
    suspend operator fun invoke(page: Int): List<Film> {
        return repository.getTopRatedFilms(page)
    }
}
