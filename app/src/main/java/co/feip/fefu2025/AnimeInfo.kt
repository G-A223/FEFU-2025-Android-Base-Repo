package co.feip.fefu2025

import android.view.ViewGroup
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import co.feip.fefu2025.ui.theme.AnimeColors
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme


@Composable
fun AnimeInfo(
    name: String,
    imageRes: Int,
    rating: String,
    year: String,
    episodes: Int,
    descr: String,
    genres: List<String>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            Text(
                text = name,
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
                    text = "Эпизоды: $episodes",
                    fontSize = 15.sp,
                )
                Text(
                    text = "Год: $year",
                    fontSize = 15.sp,
                )
                Text(
                    text = "Рейтинг: $rating",
                    fontSize = 15.sp,
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)

            ) {
                Image(
                    painter = painterResource(id = imageRes),
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

                        genres.forEach { genre ->
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
                text = descr,
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

@Preview(showBackground = true)
@Composable
fun AnimeInfoPreview() {
    FEFU2025AndroidBaseRepoTheme {
        AnimeInfo(
            name = "Берсерк",
            imageRes = R.drawable.anime10,
            rating = "9.81",
            year = "2005-2006 гг",
            episodes = 24,
            genres = listOf("Драма", "Приключения", "Экшен"),
            descr = "descr descr descr descr descr descr descr descr descr descr descr descr descr descr descr descr sega sv eesdvbsg"
            )
    }
}


