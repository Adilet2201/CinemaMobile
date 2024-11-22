package com.example.lazyrow.presentation.viewmodel

import com.example.lazyrow.data.model.FilmDetail

sealed class FilmDetailScreenState {
    object Loading : FilmDetailScreenState()
    data class Success(val filmDetail: FilmDetail) : FilmDetailScreenState()
    data class Error(val message: String) : FilmDetailScreenState()
}
