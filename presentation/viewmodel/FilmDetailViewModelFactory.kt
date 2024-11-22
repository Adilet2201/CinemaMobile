
package com.example.lazyrow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lazyrow.domain.usecase.GetFilmDetailUseCase

class FilmDetailViewModelFactory(
    private val getFilmDetailUseCase: GetFilmDetailUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilmDetailViewModel(getFilmDetailUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
