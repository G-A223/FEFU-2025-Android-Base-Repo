package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class AnimeInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val anime = Anime.animeList.random()
            val votes = listOf(
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
                (100..1000).random(),
            )

            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    AnimeInfo(
                        name = anime.name,
                        imageRes = anime.imageRes,
                        rating = anime.rating,
                        genres = anime.genres,
                        descr = anime.description,
                        year = anime.year,
                        episodes = anime.episodes
                    )
                }

                item {
                    RatingChart(votes)
                }

                item {
                    AnimeRec()
                }
            }
        }
    }
}


@Composable
fun AnimeRec() {
    val animeRecommended = Anime.animeList.shuffled().take(10)

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Может понравиться",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        LazyRow(Modifier.padding(top = 6.dp, bottom = 6.dp)) {
            items(animeRecommended) { anime ->
                AnimeCard(
                    name = anime.name,
                    imageRes = anime.imageRes,
                    rating = anime.rating,
                    genres = anime.genres
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecPreview() {
    AnimeRec()
}



