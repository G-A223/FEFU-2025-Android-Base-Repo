package co.feip.fefu2025

import android.view.ViewGroup
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel
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
        "Slice of Life" to Color(0xFF2196F3),
        "Drama" to Color(0xFFFF4141),
        "Suspense" to Color(0xFF331662),
        "Supernatural" to Color(0xFF01695F),
        "Action" to Color(0xFFFF8400),
        "Sci-Fi" to Color(0xFF5F1BDE),
        "Horror" to Color(0xFF162C1C),
        "Fantasy" to Color(0xFF8E08C2),
        "Comedy" to Color(0xFFFFC107),
        "Romance" to Color(0xFFFF00DB),
        "Adventure" to Color(0xFF00B70C),
    )
    return genreColorMap[genre] ?: Color(0xFF9E9E9E)
}



