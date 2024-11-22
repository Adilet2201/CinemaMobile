// presentation/ui/screens/HomeScreen.kt
package com.example.lazyrow.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazyrow.presentation.ui.components.SectionTitle
import com.example.lazyrow.presentation.viewmodel.FilmViewModel
import com.example.lazyrow.viewmodel.ScreenState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.navigation.NavController

@Composable
fun HomeScreen(viewModel: FilmViewModel, navController: NavController) {
    val screenState by viewModel.screenState.collectAsState()
    val isRefreshing = screenState is ScreenState.Loading

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.fetchAllData() },
    ) {
        when (screenState) {
            is ScreenState.Initial -> {
                Box(modifier = Modifier.fillMaxSize())
            }
            is ScreenState.Loading -> {
                if (!isRefreshing) {
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

                    item {
                        Text(
                            text = "Skillcinema",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        )
                    }

                    // "Now in Cinema"
                    if (successState.premieres.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Сейчас в кино")
                            LazyRowScreen(films = successState.premieres) { film ->
                                navController.navigate("film_detail/${film.id}")
                            }
                        }
                    } else {

                    }

                    // "Популярные"
                    if (successState.popularFilms.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Популярные")
                            LazyRowScreen(films = successState.popularFilms) { film ->
                                navController.navigate("film_detail/${film.id}")
                            }
                        }
                    } else {

                    }

                    // "Top Rated"
                    if (successState.topRatedFilms.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            SectionTitle(title = "Топ рейтинга")
                            LazyRowScreen(films = successState.topRatedFilms) { film ->
                                navController.navigate("film_detail/${film.id}")
                            }
                        }
                    } else {

                    }
                }
            }
            is ScreenState.Error -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ошибка загрузки данных")
                }
            }
        }
    }
}
