
package com.example.lazyrow.viewmodel

import com.example.lazyrow.data.model.Film

sealed class ScreenState {
    object Initial : ScreenState()
    object Loading : ScreenState()
    data class Success(
        val popularFilms: List<Film>,
        val topRatedFilms: List<Film>,
        val premieres: List<Film>
    ) : ScreenState()

    data class Error(val message: String) : ScreenState()
}
