package com.example.lazyrow

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lazyrow.model.Film

@Composable
fun LazyRowScreen(films: List<Film>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(films) { index, film ->
            FilmCard(film = film)
            Spacer(modifier = Modifier.width(8.dp))
        }
        // Уберите стрелку временно, чтобы проверить, не она ли вызывает проблемы
    }
}

@Composable
fun FilmCard(film: Film) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(250.dp)
            .padding(8.dp)
            .clickable {
                // Обработка нажатия на фильм
            }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(film.posterUrlPreview),
                contentDescription = film.title,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = film.title ?: film.titleEn ?: "",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(20.dp)
            )
            Text(
                text = film.genres.joinToString(", ") { it.genre },
                modifier = Modifier.padding(horizontal = 8.dp),
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }
}
