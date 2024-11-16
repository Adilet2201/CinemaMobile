package com.example.lazyrow.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape // Импорт формы
//import androidx.compose.foundation.clip // Импорт clip
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
import androidx.compose.ui.unit.dp // Импорт dp
import androidx.compose.ui.unit.sp // Импорт sp
import androidx.compose.ui.text.font.FontWeight // Импорт FontWeight
import com.example.lazyrow.presentation.ui.components.FilmCard
import com.example.lazyrow.presentation.ui.components.SectionTitle
import com.example.lazyrow.presentation.ui.theme.LazyRowTheme
import com.example.lazyrow.presentation.viewmodel.FilmViewModel
import com.example.lazyrow.presentation.viewmodel.FilmViewModelFactory
import com.example.lazyrow.data.api.RetrofitInstance
import com.example.lazyrow.data.repository.FilmRepository
import com.example.lazyrow.domain.repository.FilmRepositoryInterface
import com.example.lazyrow.domain.usecase.GetPopularFilmsUseCase
import com.example.lazyrow.domain.usecase.GetPremieresUseCase
import com.example.lazyrow.domain.usecase.GetTopRatedFilmsUseCase
import com.example.lazyrow.presentation.ui.screens.HomeScreen
import com.example.lazyrow.presentation.ui.screens.ProfileScreen
import com.example.lazyrow.presentation.ui.screens.SearchScreen
import com.example.lazyrow.viewmodel.ScreenState
import com.google.accompanist.pager.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazyrow.R
import com.example.lazyrow.utils.MovieItem
import com.example.lazyrow.presentation.ui.navigation.BottomNavItem
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.compose.foundation.clip

class MainActivity : ComponentActivity() {
    companion object {
        val items: List<MovieItem> = listOf(
            MovieItem(
                title = "Новинка 1",
                genres = listOf("Драма"),
                countries = listOf("Россия"),
                rating = "7.8",
                image = R.drawable.img,
                isPremiere = true
            ),
            MovieItem(
                title = "Новинка 2",
                genres = listOf("Приключения"),
                countries = listOf("США"),
                rating = "8.2",
                image = R.drawable.img,
                isPremiere = true
            ),
            MovieItem(
                title = "Популярный фильм 1",
                genres = listOf("Боевик"),
                countries = listOf("США"),
                rating = "8.5",
                image = R.drawable.img,
                isPopular = true
            ),
            MovieItem(
                title = "Популярный фильм 2",
                genres = listOf("Комедия"),
                countries = listOf("Франция"),
                rating = "7.9",
                image = R.drawable.img,
                isPopular = true
            ),
            MovieItem(
                title = "Топ фильм 1",
                genres = listOf("Драма"),
                countries = listOf("Великобритания"),
                rating = "9.0",
                image = R.drawable.img,
                isTop250 = true
            ),
            MovieItem(
                title = "Топ фильм 2",
                genres = listOf("Триллер"),
                countries = listOf("Германия"),
                rating = "8.8",
                image = R.drawable.img,
                isTop250 = true
            ),
            MovieItem(
                title = "Побег из Шоушенка",
                genres = listOf("Драма"),
                countries = listOf("США"),
                rating = "9.3",
                image = R.drawable.img,
                isTop250 = true
            ),
            MovieItem(
                title = "Крестный отец",
                genres = listOf("Криминал", "Драма"),
                countries = listOf("США"),
                rating = "9.2",
                image = R.drawable.img,
                isTop250 = true
            )
            // Добавьте дополнительные фильмы при необходимости
        )
    }

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
        val navController = rememberNavController() // <-- Теперь функция доступна
        NavHost(navController = navController, startDestination = "onboarding") { // <-- NavHost доступен
            composable("onboarding") {
                OnboardingScreen(navController = navController)
            }
            composable("main") {
                MainScreen()
            }
        }
    }

    data class OnboardingPage(val image: Int, val title: String)

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnboardingScreen(navController: NavController) {
        val onboardingPages = listOf(
            OnboardingPage(
                image = R.drawable.man_netflix,
                title = "Узнавай о премьерах"
            ),
            OnboardingPage(
                image = R.drawable.girl,
                title = "Создавай коллекции"
            ),
            OnboardingPage(
                image = R.drawable.share,
                title = "Делись с друзьями"
            )
        )

        val pagerState = rememberPagerState()

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = "Skillcinema",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 25.dp, top = 30.dp)
                        .align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalPager(
                    state = pagerState,
                    count = onboardingPages.size,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    OnboardingPageContent(onboardingPages[page])
                }
            }

            Text(
                text = "Пропустить",
                fontSize = 19.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 35.dp, end = 16.dp)
                    .clickable {
                        navController.navigate("main") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
            )

            // Точки внизу
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 100.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(onboardingPages.size) { index ->
                    val color = if (index == pagerState.currentPage) Color.Black else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(8.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(color)
                    )
                }
            }
        }
    }

    @Composable
    fun OnboardingPageContent(page: OnboardingPage) {
        val titleParts = page.title.split(" ", limit = 2)
        val firstWord = titleParts.getOrElse(0) { "" }
        val remainingWords = titleParts.getOrElse(1) { "" }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .width(350.dp)
                    .height(300.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = firstWord,
                    fontSize = 35.sp,
                    lineHeight = 40.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = remainingWords,
                    fontSize = 35.sp,
                    lineHeight = 40.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
        }
    }

    @Composable
    fun MainScreen() {
        var selectedIndex by remember { mutableStateOf(0) }

        // Инициализация зависимостей
        val apiService = RetrofitInstance.apiService
        val filmRepository: FilmRepositoryInterface = FilmRepository(apiService)
        val getPopularFilmsUseCase = GetPopularFilmsUseCase(filmRepository)
        val getTopRatedFilmsUseCase = GetTopRatedFilmsUseCase(filmRepository)
        val getPremieresUseCase = GetPremieresUseCase(filmRepository)

        // Создание фабрики ViewModel
        val factory = FilmViewModelFactory(
            getPopularFilmsUseCase,
            getTopRatedFilmsUseCase,
            getPremieresUseCase
        )

        // Получение ViewModel с использованием фабрики
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
                    0 -> HomeScreen(viewModel = viewModel)
                    1 -> SearchScreen()
                    2 -> ProfileScreen()
                }
            }
        }
    }
}
