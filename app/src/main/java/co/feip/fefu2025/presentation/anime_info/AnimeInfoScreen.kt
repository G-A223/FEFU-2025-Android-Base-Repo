package co.feip.fefu2025.presentation.anime_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.feip.fefu2025.AnimeCard
import co.feip.fefu2025.AnimeInfo
import co.feip.fefu2025.ErrorView
import co.feip.fefu2025.LoadingView
import co.feip.fefu2025.RatingChart
import co.feip.fefu2025.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeInfoScreen(
    viewModel: AnimeInfoViewModel,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    val animeData = viewModel.anime.value
    val votes = viewModel.ratings.value
    val recs = viewModel.recommended.value
    val isLoading by viewModel.isLoading_public.collectAsState()
    val error by viewModel.error_public.collectAsState()

    LaunchedEffect(Unit) {
        if (animeData == null && !isLoading && error == null) {
            viewModel.retry()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                ),
                title = {
                    if (!isLoading || error == null) {
                        Text(text = animeData?.name.toString())
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { paddings ->
        when {
            isLoading -> LoadingView()
            error != null -> ErrorView(
                message = error!!,
                onRetry = { viewModel.retry() }
            )

            animeData == null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Не удалось загрузить данные")
                }
            }

            else -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding((paddings))
                ) {
                    item {
                        AnimeInfo(viewModel)
                    }

                    item {
                        RatingChart(votes)
                    }

                    item {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                        ) {
                            Text(
                                text = "Может понравиться",
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                modifier = Modifier.clickable {
                                    navController.navigate(Destination.Recommended.route)
                                }
                            )

                            LazyRow(Modifier.padding(top = 6.dp, bottom = 6.dp)) {
                                items(recs) { anime ->
                                    AnimeCard(
                                        name = anime.name,
                                        imageUrl = anime.imageUrl,
                                        rating = anime.rating,
                                        genres = anime.genres,
                                        onClick = {
                                            navController.navigate(Destination.AnimeInfo(anime.id).route)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
