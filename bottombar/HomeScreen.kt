package com.example.lazyrow.bottombar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazyrow.LazyRowScreen
import com.example.lazyrow.SectionTitle
import com.example.lazyrow.viewmodel.FilmViewModel

@Composable
fun HomeScreen(viewModel: FilmViewModel) {
    val popularFilms by viewModel.popularFilms.collectAsState()
    val topRatedFilms by viewModel.topRatedFilms.collectAsState()
    val premieres by viewModel.premieres.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Заголовок приложения
        item {
            Text(
                text = "Skillcinema",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )
        }

        // Секция "Сейчас в кино"
        if (premieres.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SectionTitle(title = "Сейчас в кино")
                LazyRowScreen(films = premieres)
            }
        }

        // Секция "Популярные"
        if (popularFilms.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SectionTitle(title = "Популярные")
                LazyRowScreen(films = popularFilms)
            }
        }

        // Секция "Топ рейтинга"
        if (topRatedFilms.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SectionTitle(title = "Топ рейтинга")
                LazyRowScreen(films = topRatedFilms)
            }
        }
    }
}
