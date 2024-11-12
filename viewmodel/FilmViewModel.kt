package com.example.lazyrow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazyrow.model.Film
import com.example.lazyrow.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmViewModel : ViewModel() {

    private val repository = FilmRepository()

    private val _popularFilms = MutableStateFlow<List<Film>>(emptyList())
    val popularFilms: StateFlow<List<Film>> = _popularFilms

    private val _topRatedFilms = MutableStateFlow<List<Film>>(emptyList())
    val topRatedFilms: StateFlow<List<Film>> = _topRatedFilms

    private val _premieres = MutableStateFlow<List<Film>>(emptyList())
    val premieres: StateFlow<List<Film>> = _premieres

    init {
        fetchPopularFilms()
        fetchTopRatedFilms()
        fetchPremieres()
    }

    private fun fetchPopularFilms() {
        viewModelScope.launch {
            try {
                val films = repository.getPopularFilms()
                _popularFilms.value = films
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchTopRatedFilms() {
        viewModelScope.launch {
            try {
                val films = repository.getTopRatedFilms()
                _topRatedFilms.value = films
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchPremieres() {
        viewModelScope.launch {
            try {
                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                val monthInt = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
                val monthNames = listOf(
                    "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
                )
                val currentMonth = monthNames[monthInt]

                val films = repository.getPremieres(year = currentYear, month = currentMonth)
                _premieres.value = films
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
