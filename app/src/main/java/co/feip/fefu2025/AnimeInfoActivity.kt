package co.feip.fefu2025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class AnimeInfoActivity : ComponentActivity() {
    private val genre_dict = mapOf(
        "Сёнен" to R.color.blue,
        "Драма" to R.color.red,
        "Сэйнэн" to R.color.blue2,
        "Тайна" to R.color.teal_700,
        "Триллер" to R.color.grey,
        "Экшен" to R.color.orange,
        "Фантастика" to R.color.purple,
        "Авангард" to R.color.red_pink,
        "Ужасы" to R.color.dark_green,
        "Фэнтези" to R.color.purple2,
        "Комедия" to R.color.yellow,
        "Сёдзё" to R.color.light_pink,
        "Романтика" to R.color.pink,
        "Дзёсей" to R.color.brown_orange,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anime_info)

        val anime = Anime.animeList.random()

        val name = findViewById<TextView>(R.id.name_info)
        name.text = anime.name

        val episodes = findViewById<TextView>(R.id.ep_info)
        episodes.text = "${episodes.text} ${anime.episodes}"

        val rating = findViewById<TextView>(R.id.rating_info)
        rating.text = "${rating.text} ${anime.rating}"

        val year = findViewById<TextView>(R.id.year_info)
        year.text = "${year.text} ${anime.year}"

        val descr = findViewById<TextView>(R.id.descr_info)
        descr.text = anime.description

        val img = findViewById<ImageView>(R.id.image_info)
        img.setImageResource(anime.imageRes)

        val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.genres_info)
        for (genre in anime.genres) {
            val newGenreView = AnimeGenre(this).apply {
                setName(genre)
                setColor(genre_dict[genre] ?: R.color.black)
            }
            flexBoxLayout.addView(newGenreView)
        }
    }
}

