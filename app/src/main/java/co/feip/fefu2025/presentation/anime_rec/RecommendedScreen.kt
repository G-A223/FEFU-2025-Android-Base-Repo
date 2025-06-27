package co.feip.fefu2025.presentation.anime_rec

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.AnimeCard
import co.feip.fefu2025.ErrorView
import co.feip.fefu2025.LoadingView
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.dependency.ProvideRecommendedData
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel
import co.feip.fefu2025.presentation.anime_list.RecommendedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedScreen(
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: RecommendedViewModel = viewModel(
        factory = ProvideRecommendedData.provideAnimeListViewModel()
    )

    val animeData by viewModel.animeList.collectAsState()
    val recommendedAnime = remember(animeData) {
        if (animeData.isNotEmpty()) animeData.shuffled().take(10) else emptyList()
    }
    val isLoading by viewModel.isLoading_public.collectAsState()
    val error by viewModel.error_public.collectAsState()

    val rows = remember(recommendedAnime) { recommendedAnime.chunked(2) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                ),
                title = { Text("Может понравиться") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddings ->
        when {
            isLoading -> LoadingView()
            error != null -> ErrorView(
                message = error!!,
                onRetry = { }
            )

            animeData.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Нет данных для отображения")
                }

            }

            else -> {
                Column(modifier = Modifier.padding(paddings)) {
                    LazyColumn {
                        items(rows) { row ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                row.forEach { anime ->
                                    AnimeCard(
                                        name = anime.name,
                                        imageRes = anime.imageRes,
                                        rating = anime.rating,
                                        genres = anime.genres,
                                        onClick = { onAnimeClick(anime.id) }
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