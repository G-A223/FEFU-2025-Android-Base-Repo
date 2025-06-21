package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import co.feip.fefu2025.R

class AnimeCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attributeSet, defStyleAttr) {
    private val image: ImageView
    private val animeName: TextView
//    private val genres: AnimeGenre
    private val rating: RatingView

    init {
        LayoutInflater.from(context).inflate(R.layout.anime_card, this, true)
        image = findViewById(R.id.image)
        animeName = findViewById(R.id.animeName)
//        genres = findViewById(R.id.genres)
        rating = findViewById(R.id.ratingVal)
    }

    fun setImage(src: Int) {
        image.setImageResource(src)
    }

    fun setName(name: String) {
        animeName.text = name
    }

    fun setRating(score: String) {
        rating.setRating(score)
    }
}