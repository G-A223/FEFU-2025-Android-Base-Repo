package co.feip.fefu2025

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import co.feip.fefu2025.R

class AnimeGenre @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attributeSet, defStyleAttr) {
    private val genreName: TextView
    private val boxColor: View

    init {
        LayoutInflater.from(context).inflate(R.layout.anime_genre, this, true)
        genreName = findViewById(R.id.genreName)
        boxColor = findViewById(R.id.boxColor)

        genreName.viewTreeObserver.addOnPreDrawListener {
            updateBoxSize()
            true
        }
    }

    private fun updateBoxSize() {
        val box_width = boxColor.layoutParams
        box_width.width = genreName.width
        boxColor.layoutParams = box_width
    }

    fun setName(name: String) {
        genreName.text = name
    }

    fun setColor(colorRes: Int) {
        val color = ContextCompat.getColor(context, colorRes)
        boxColor.backgroundTintList = ColorStateList.valueOf(color)
    }
}