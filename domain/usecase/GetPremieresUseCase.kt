// domain/usecase/GetPremieresUseCase.kt
package com.example.lazyrow.domain.usecase

import com.example.lazyrow.data.model.Film
import com.example.lazyrow.domain.repository.FilmRepositoryInterface

class GetPremieresUseCase(private val repository: FilmRepositoryInterface) {
    suspend operator fun invoke(year: Int, month: String): List<Film> {
        return repository.getPremieres(year, month)
    }
}
