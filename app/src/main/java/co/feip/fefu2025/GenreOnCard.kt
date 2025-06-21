package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import co.feip.fefu2025.R

class GenreOnCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attributeSet, defStyleAttr) {
    private val genreName: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.genre_on_card, this, true)
        genreName = findViewById(R.id.genre)

    }

    fun setName(name: String) {
        genreName.text = name
    }
}