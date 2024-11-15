package com.example.lazyrow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazyrow.model.Film
import com.example.lazyrow.repository.FilmRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lazyrow.viewmodel.ScreenState


class FilmViewModel : ViewModel() {

    private val repository = FilmRepository()

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        fetchAllData()
    }

    fun fetchAllData() { // Changed from private to public for retry
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            try {
                // Fetch data concurrently
                val popularFilmsDeferred = viewModelScope.async { repository.getPopularFilms() }
                val topRatedFilmsDeferred = viewModelScope.async { repository.getTopRatedFilms() }
                val premieresDeferred = viewModelScope.async {
                    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                    val monthInt = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
                    val monthNames = listOf(
                        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
                    )
                    val currentMonth = monthNames[monthInt]
                    repository.getPremieres(year = currentYear, month = currentMonth)
                }

                // Await results
                val popularFilms = popularFilmsDeferred.await()
                val topRatedFilms = topRatedFilmsDeferred.await()
                val premieres = premieresDeferred.await()

                // Update state to Success
                _screenState.value = ScreenState.Success(
                    popularFilms = popularFilms,
                    topRatedFilms = topRatedFilms,
                    premieres = premieres
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = ScreenState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }
        }
    }
}