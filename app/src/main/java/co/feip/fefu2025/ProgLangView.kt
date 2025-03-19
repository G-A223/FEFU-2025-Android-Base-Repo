package co.feip.fefu2025

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class ProgLangView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
    private val langName: TextView
    private val circleColor: View
    private val percentage: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.prog_lang, this, true)
        langName = findViewById(R.id.langName)
        circleColor = findViewById(R.id.circleColor)
        percentage = findViewById(R.id.percentage)
    }

    fun setName(name: String) {
        langName.text = name
    }

    fun setPercent(percent: Float) {
        percentage.text = "${"%.2f".format(percent)}%"
    }

    fun setColor(color: Int) {
        circleColor.background.setTint(color)
    }
}