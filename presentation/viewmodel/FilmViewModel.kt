
package com.example.lazyrow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazyrow.domain.usecase.*
import com.example.lazyrow.viewmodel.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmViewModel(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val getTopRatedFilmsUseCase: GetTopRatedFilmsUseCase,
    private val getPremieresUseCase: GetPremieresUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        fetchAllData()
    }

    fun fetchAllData() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            try {
                val popularFilms = getPopularFilmsUseCase(1)
                val topRatedFilms = getTopRatedFilmsUseCase(1)
                val premieres = getPremieresUseCase(2023, "SEPTEMBER")

                _screenState.value = ScreenState.Success(
                    popularFilms = popularFilms,
                    topRatedFilms = topRatedFilms,
                    premieres = premieres
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value =
                    ScreenState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
            }
        }
    }
}
