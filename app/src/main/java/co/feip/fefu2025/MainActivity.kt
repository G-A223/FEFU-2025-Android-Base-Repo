package co.feip.fefu2025

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.flexBox)

        for (anime in Anime.animeList) {
            val newCardView = AnimeCard(this).apply {
                setName(anime.name)
                setImage(anime.imageRes)
                setRating(anime.rating)
            }
            flexBoxLayout.addView(newCardView)

            val curGenres = anime.genres.take(4)

            val genreLayout = newCardView.findViewById<LinearLayout>(R.id.genres)
            genreLayout.removeAllViews()
            val availableWidth = genreLayout.width - genreLayout.paddingLeft - genreLayout.paddingRight
            var usedWidth = 0

            genreLayout.post {
                val cardWidth = genreLayout.width - genreLayout.paddingLeft - genreLayout.paddingRight
                var curWidth = 0

                for (genre in anime.genres) {
                    val newGenre = GenreOnCard(this).apply {
                        setName(genre)
                        measure(
                            View.MeasureSpec.UNSPECIFIED,
                            View.MeasureSpec.UNSPECIFIED
                        )
                    }

                    if (cardWidth >= newGenre.measuredWidth + curWidth) {
                        curWidth += newGenre.measuredWidth
                        genreLayout.addView(newGenre)
                    } else {
                        break
                    }
                }
            }
        }
    }
}

