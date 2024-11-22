package com.example.lazyrow.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazyrow.presentation.ui.components.FilmCard
import com.example.lazyrow.data.model.Film

@Composable
fun LazyRowScreen(films: List<Film>, onFilmClick: (Film) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(films) { index, film ->
            FilmCard(film = film, onClick = { onFilmClick(film) })
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
