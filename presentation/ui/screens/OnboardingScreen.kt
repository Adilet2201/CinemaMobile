
package com.example.lazyrow.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.lazyrow.R
import com.google.accompanist.pager.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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

        // Dots Indicator at the bottom
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

data class OnboardingPage(val image: Int, val title: String)

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
