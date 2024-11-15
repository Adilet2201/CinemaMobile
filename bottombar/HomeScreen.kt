package com.example.lazyrow.bottombar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazyrow.LazyRowScreen
import com.example.lazyrow.SectionTitle
import com.example.lazyrow.viewmodel.FilmViewModel
import com.example.lazyrow.viewmodel.ScreenState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(viewModel: FilmViewModel) {
    val screenState by viewModel.screenState.collectAsState()
    val isRefreshing = screenState is ScreenState.Loading


    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.fetchAllData() },
    ) {
        when (screenState) {
            is ScreenState.Initial -> {
                // Optionally, display nothing or a placeholder
                Box(modifier = Modifier.fillMaxSize())
            }
            is ScreenState.Loading -> {
                if (!isRefreshing) { // Avoid showing another loader if already refreshing
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is ScreenState.Success -> {
                val successState = screenState as ScreenState.Success
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    // App Title
                    item {
                        Text(
                            text = "Skillcinema",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        )
                    }

                    // "Now in Cinema" Section
                    if (successState.premieres.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Сейчас в кино")
                            LazyRowScreen(films = successState.premieres)
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Сейчас в кино")
                            Text(
                                text = "Нет новых премьеров",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }


                    if (successState.popularFilms.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Популярные")
                            LazyRowScreen(films = successState.popularFilms)
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Популярные")
                            Text(
                                text = "Нет популярных фильмов",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // "Top Rated" Section
                    if (successState.topRatedFilms.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Топ рейтинга")
                            LazyRowScreen(films = successState.topRatedFilms)
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Топ рейтинга")
                            Text(
                                text = "Нет топовых фильмов",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            is ScreenState.Error -> {
                val errorState = screenState as ScreenState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ошибка: ${errorState.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchAllData() }) {
                            Text(text = "Повторить")
                        }
                    }
                }
            }
        }
    }
}