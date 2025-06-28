package co.feip.fefu2025

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.dependency.ProvideRecommendedData
import co.feip.fefu2025.navigation.Destination
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel
import co.feip.fefu2025.presentation.anime_list.RecommendedViewModel
import co.feip.fefu2025.ui.theme.AnimeColors
import coil.compose.AsyncImage

@Composable
fun AnimeInfo(
    viewModel: AnimeInfoViewModel,
    modifier: Modifier = Modifier
) {
    val animeData = viewModel.anime.value

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            Text(
                text = animeData?.name ?: "name",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 2.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Эпизоды: ${animeData?.episodes}",
                    fontSize = 15.sp,
                )
                Text(
                    text = "Год: ${animeData?.year}",
                    fontSize = 15.sp,
                )
                Text(
                    text = "Рейтинг: ${animeData?.rating}",
                    fontSize = 15.sp,
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)

            ) {
                AsyncImage(
                    model = animeData?.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            AndroidView(
                factory = { context ->
                    FlexBoxLayout(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )

                        animeData?.genres?.forEach { genre ->
                            addView(
                                androidx.compose.ui.platform.ComposeView(context).apply {
                                    setContent {
                                        AnimeGenreInfo(
                                            genre = genre,
                                            modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = animeData?.description!!,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun AnimeGenreInfo(genre: String, modifier: Modifier) {
    val genreColor = genreColor(genre)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(genreColor)
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Text(
            text = genre,
            fontSize = 18.sp,
            color = AnimeColors.white
        )
    }
}

@Composable
fun genreColor(genre: String): Color {
    val genreColorMap = mapOf(
        "Сёнен" to Color(0xFF2196F3),
        "Драма" to Color(0xFFFF4141),
        "Сэйнэн" to Color(0xFF3F51B5),
        "Тайна" to Color(0xFF331662),
        "Триллер" to Color(0xFF01695F),
        "Экшен" to Color(0xFFFF8400),
        "Фантастика" to Color(0xFF5F1BDE),
        "Авангард" to Color(0xFFFF0082),
        "Ужасы" to Color(0xFF162C1C),
        "Фэнтези" to Color(0xFF8E08C2),
        "Комедия" to Color(0xFFFFC107),
        "Сёдзё" to Color(0xFFFF00DB),
        "Романтика" to Color(0xFFEA58FF),
        "Дзёсей" to Color(0xFF6AF6AB),
        "Приключения" to Color(0xFF00B70C),
    )
    return genreColorMap[genre] ?: Color(0xFF9E9E9E)
}

@Composable
fun AnimeRec(
    onAnimeClick: (Int) -> Unit,
    navController: NavHostController,
    ) {
    val viewModel: RecommendedViewModel = viewModel(
        factory = ProvideRecommendedData.provideAnimeListViewModel()
    )

    val animeRecommended = remember { viewModel.getWithoutSuspend().shuffled().take(10)  }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Может понравиться",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.clickable { navController.navigate(Destination.Recommended.route)  }
        )

        LazyRow(Modifier.padding(top = 6.dp, bottom = 6.dp)) {
            items(animeRecommended) { anime ->
                AnimeCard(
                    name = anime.name,
                    imageUrl = anime.imageUrl,
                    rating = anime.rating,
                    genres = anime.genres,
                    onClick = { onAnimeClick(anime.id) }
                )
            }
        }
    }
}


