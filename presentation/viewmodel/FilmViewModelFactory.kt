
package com.example.lazyrow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lazyrow.domain.usecase.*

class FilmViewModelFactory(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val getTopRatedFilmsUseCase: GetTopRatedFilmsUseCase,
    private val getPremieresUseCase: GetPremieresUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)) {
            return FilmViewModel(
                getPopularFilmsUseCase,
                getTopRatedFilmsUseCase,
                getPremieresUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
