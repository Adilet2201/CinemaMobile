
package com.example.lazyrow.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lazyrow.presentation.viewmodel.FilmDetailViewModel
import com.example.lazyrow.presentation.viewmodel.FilmDetailScreenState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.lazyrow.R
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.lazyrow.data.model.FilmDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailScreen(
    filmId: Int,
    viewModel: FilmDetailViewModel,
    navController: NavController
) {
    val filmDetailState by viewModel.filmDetailState.collectAsState()

    LaunchedEffect(filmId) {
        viewModel.fetchFilmDetail(filmId)
    }

    when (filmDetailState) {
        is FilmDetailScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is FilmDetailScreenState.Success -> {
            val filmDetail = (filmDetailState as FilmDetailScreenState.Success).filmDetail
            FilmDetailContent(filmDetail = filmDetail, navController = navController)
        }
        is FilmDetailScreenState.Error -> {
            val message = (filmDetailState as FilmDetailScreenState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Ошибка: $message")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailContent(filmDetail: FilmDetail, navController: NavController) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.caret_left),
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Постер
            Image(
                painter = rememberAsyncImagePainter(filmDetail.posterUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Название
            Text(
                text = filmDetail.nameRu ?: filmDetail.nameEn ?: "Название недоступно",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Описание
            Text(
                text = filmDetail.description ?: "Описание недоступно.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Год выпуска: ${filmDetail.year ?: "неизвестно"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Длительность: ${filmDetail.filmLength ?: "неизвестно"} мин.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Страны: ${filmDetail.countries.joinToString(", ") { it.country }}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Жанры: ${filmDetail.genres.joinToString(", ") { it.genre }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
