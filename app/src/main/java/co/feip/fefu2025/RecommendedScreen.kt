package co.feip.fefu2025

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel
import co.feip.fefu2025.ui.theme.AnimeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedScreen(
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: AnimeListViewModel = viewModel(
        factory = ProvideAnimeListData.provideAnimeListViewModel()
    )

    val animeData by viewModel.animeList.collectAsState()
    val recommendedAnime = remember { animeData.shuffled().take(10) }
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