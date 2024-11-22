package com.example.lazyrow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazyrow.data.model.FilmDetail
import com.example.lazyrow.domain.usecase.GetFilmDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmDetailViewModel(private val getFilmDetailUseCase: GetFilmDetailUseCase) : ViewModel() {

    private val _filmDetailState = MutableStateFlow<FilmDetailScreenState>(FilmDetailScreenState.Loading)
    val filmDetailState: StateFlow<FilmDetailScreenState> = _filmDetailState

    fun fetchFilmDetail(id: Int) {
        viewModelScope.launch {
            try {
                val filmDetail = getFilmDetailUseCase(id)
                _filmDetailState.value = FilmDetailScreenState.Success(filmDetail)
            } catch (e: Exception) {
                _filmDetailState.value = FilmDetailScreenState.Error(e.localizedMessage ?: "Error fetching film details")
            }
        }
    }
}
