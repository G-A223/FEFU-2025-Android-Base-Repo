package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn


class AnimeInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val anime = Anime.animeList.random()
        LazyColumn {
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
        }
        }
    }
}


