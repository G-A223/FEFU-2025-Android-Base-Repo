package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeScreen()
        }
    }
}


@Composable
fun AnimeScreen() {
    val rows = remember(Anime.animeList) {
        Anime.animeList.chunked(2)
    }

    LazyColumn{
        items(rows) { pair ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                pair.forEach { anime ->
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
}
