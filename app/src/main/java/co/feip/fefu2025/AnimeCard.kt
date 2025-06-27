package co.feip.fefu2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import co.feip.fefu2025.ui.theme.AnimeColors
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme


@Composable
fun AnimeCard(
    name: String,
    imageRes: Int,
    rating: String,
    genres: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .width(180.dp)
            .padding(4.dp),
    ) {
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)

            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                )

                RatingView(
                    rating = rating,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }

            Column {
                GenreRow(genres = genres)

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun AnimeGenre(genre: String, modifier: Modifier) {
    Text(
        text = genre,
        style = MaterialTheme.typography.labelSmall,
        color = AnimeColors.grey
    )
}

@Composable
fun RatingView(rating: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
            .background(AnimeColors.grey)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .shadow(8.dp)
    ) {
        Text(
            text = rating,
            color = AnimeColors.white,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GenreRow(genres: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        genres.take(2).forEach { genre ->
            AnimeGenre(genre = genre, Modifier)
        }

        if (genres.size > 2) {
            Text(
                text = "+${genres.size - 2}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimeCardPreview() {
    FEFU2025AndroidBaseRepoTheme {
        AnimeCard(
            name = "Берсерк",
            imageRes = R.drawable.anime10,
            rating = "9.8",
            genres = listOf("Драма", "Приключения", "Экшен"),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}