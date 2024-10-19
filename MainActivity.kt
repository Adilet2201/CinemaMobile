@file:Suppress("DEPRECATION")

package com.example.task1



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lazyrow.LazyRowScreen
import com.google.accompanist.pager.*

data class MovieItem(
    val title: String,
    val zhanr: String,
    val rating: String,
    val image: Int
)
class MainActivity : ComponentActivity() {
    companion object {
        val items: List<MovieItem> = listOf(
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
            MovieItem(
                title = "Близкие",
                zhanr = "Драма",
                rating = "10",
                image = R.drawable.img
            ),
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
        composable("lazy_row_screen") {
            LazyRowScreen()

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
    val scope = rememberCoroutineScope()

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
                OnboardingPageContent(onboardingPages[page], pagerState.currentPage)
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
                    navController.navigate("lazy_row_screen")  // Navigate to LazyRowScreen
                }
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)  // Keep dots in the bottom left corner
                .padding(start = 20.dp, bottom = 100.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            repeat(onboardingPages.size) { index ->
                val color = if (index == pagerState.currentPage) Color.Black else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(2.dp)  // Small padding between dots
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage, currentPage: Int) {
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


