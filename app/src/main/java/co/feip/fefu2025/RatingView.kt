package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import co.feip.fefu2025.R

class RatingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attributeSet, defStyleAttr) {
    private val rating: TextView
    private val boxColor: View

    init {
        LayoutInflater.from(context).inflate(R.layout.rating, this, true)
        rating = findViewById(R.id.rating)
        boxColor = findViewById(R.id.boxColor)
    }

    fun setRating(score: String) {
        rating.text = score
    }
}