package com.example.lazyrow.domain.usecase

import com.example.lazyrow.data.model.FilmDetail
import com.example.lazyrow.domain.repository.FilmRepositoryInterface

class GetFilmDetailUseCase(private val repository: FilmRepositoryInterface) {
    suspend operator fun invoke(id: Int): FilmDetail {
        return repository.getFilmDetailById(id)
    }
}
