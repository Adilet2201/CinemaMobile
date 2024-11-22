
package com.example.lazyrow.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lazyrow.data.model.Film

@Composable
fun FilmCard(film: Film, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(250.dp)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(film.posterUrlPreview),
                contentDescription = film.title ?: film.titleEn ?: "",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
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
