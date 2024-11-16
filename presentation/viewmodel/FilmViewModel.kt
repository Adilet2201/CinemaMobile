// presentation/viewmodel/FilmViewModel.kt
package com.example.lazyrow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazyrow.data.model.Film
import com.example.lazyrow.domain.usecase.GetPopularFilmsUseCase
import com.example.lazyrow.domain.usecase.GetPremieresUseCase
import com.example.lazyrow.domain.usecase.GetTopRatedFilmsUseCase
import com.example.lazyrow.viewmodel.ScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

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
                // Получение текущего года и месяца
                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                val monthInt = calendar.get(Calendar.MONTH)
                val monthNames = listOf(
                    "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
                )
                val currentMonth = monthNames.getOrElse(monthInt) { "JANUARY" }

                // Запуск запросов параллельно
                val popularFilmsDeferred = async { getPopularFilmsUseCase() }
                val topRatedFilmsDeferred = async { getTopRatedFilmsUseCase() }
                val premieresDeferred = async { getPremieresUseCase(year = currentYear, month = currentMonth) }

                // Ожидание результатов
                val popularFilms = popularFilmsDeferred.await()
                val topRatedFilms = topRatedFilmsDeferred.await()
                val premieres = premieresDeferred.await()

                // Обновление состояния
                _screenState.value = ScreenState.Success(
                    popularFilms = popularFilms,
                    topRatedFilms = topRatedFilms,
                    premieres = premieres
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = ScreenState.Error(e.localizedMessage ?: "Произошла неожиданная ошибка.")
            }
        }
    }
}
