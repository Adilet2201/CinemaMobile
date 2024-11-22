
package com.example.lazyrow.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazyrow.presentation.ui.theme.LazyRowTheme
import com.example.lazyrow.presentation.viewmodel.FilmViewModel
import com.example.lazyrow.presentation.viewmodel.FilmViewModelFactory
import com.example.lazyrow.data.api.RetrofitInstance
import com.example.lazyrow.data.repository.FilmRepository
import com.example.lazyrow.domain.repository.FilmRepositoryInterface
import com.example.lazyrow.domain.usecase.GetPopularFilmsUseCase
import com.example.lazyrow.domain.usecase.GetPremieresUseCase
import com.example.lazyrow.domain.usecase.GetTopRatedFilmsUseCase
import com.example.lazyrow.domain.usecase.GetFilmDetailUseCase
import com.example.lazyrow.presentation.ui.screens.HomeScreen
import com.example.lazyrow.presentation.ui.screens.ProfileScreen
import com.example.lazyrow.presentation.ui.screens.SearchScreen
import com.example.lazyrow.presentation.ui.screens.FilmDetailScreen
import com.example.lazyrow.presentation.ui.screens.OnboardingScreen
import com.example.lazyrow.presentation.viewmodel.FilmDetailViewModel
import com.example.lazyrow.presentation.viewmodel.FilmDetailViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazyrow.presentation.ui.navigation.BottomNavItem
import androidx.navigation.NavController // <-- Add this import
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {

    private val bottomNavItems = listOf(
        BottomNavItem(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            title = "Search",
            route = "search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        BottomNavItem(
            title = "Profile",
            route = "profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyRowTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "onboarding") {
            composable("onboarding") {
                OnboardingScreen(navController = navController)
            }
            composable("main") {
                MainScreen(navController = navController)
            }
            composable(
                "film_detail/{filmId}",
                arguments = listOf(navArgument("filmId") { type = NavType.IntType })
            ) { backStackEntry ->
                val filmId = backStackEntry.arguments?.getInt("filmId") ?: return@composable
                // Initialize dependencies
                val apiService = RetrofitInstance.apiService
                val filmRepository: FilmRepositoryInterface = FilmRepository(apiService)
                val getFilmDetailUseCase = GetFilmDetailUseCase(filmRepository)
                val filmDetailViewModelFactory = FilmDetailViewModelFactory(getFilmDetailUseCase)
                val filmDetailViewModel: FilmDetailViewModel = viewModel(factory = filmDetailViewModelFactory)

                FilmDetailScreen(
                    filmId = filmId,
                    viewModel = filmDetailViewModel,
                    navController = navController
                )
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController) {
        var selectedIndex by remember { mutableStateOf(0) }

        // Initialize dependencies
        val apiService = RetrofitInstance.apiService
        val filmRepository: FilmRepositoryInterface = FilmRepository(apiService)
        val getPopularFilmsUseCase = GetPopularFilmsUseCase(filmRepository)
        val getTopRatedFilmsUseCase = GetTopRatedFilmsUseCase(filmRepository)
        val getPremieresUseCase = GetPremieresUseCase(filmRepository)

        // Create ViewModel factory
        val factory = FilmViewModelFactory(
            getPopularFilmsUseCase,
            getTopRatedFilmsUseCase,
            getPremieresUseCase
        )

        // Obtain ViewModel using the factory
        val viewModel: FilmViewModel = viewModel(factory = factory)

        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, bottomNavItem ->
                        NavigationBarItem(
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedIndex) {
                                        bottomNavItem.selectedIcon
                                    } else {
                                        bottomNavItem.unselectedIcon
                                    },
                                    contentDescription = bottomNavItem.title
                                )
                            },
                            label = { Text(text = bottomNavItem.title) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedIndex) {
                    0 -> HomeScreen(viewModel = viewModel, navController = navController)
                    1 -> SearchScreen()
                    2 -> ProfileScreen()
                }
            }
        }
    }
}
