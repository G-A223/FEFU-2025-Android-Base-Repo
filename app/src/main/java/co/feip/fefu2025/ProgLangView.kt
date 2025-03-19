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


    /*
    private var langName: String? = ""
    private var circleColor: Int = Color.BLACK
    private var percentage: Float = 0.0F

    private var binding: ProgLangBinding =
        ProgLangBinding.inflate(LayoutInflater.from(context), this, false)


    private var _langName: String = ""
        get() = binding.langName.text.toString()
        set(langName) {
            _langName = name
            binding.langName.text = _name
        }

    init {
        addView(binding.root)
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ProgLangView, 0, 0
        )

        val langName = typedArray.getString(R.styleable.ProgLangView_name)
        val percentage = typedArray.getFloat(R.styleable.ProgLangView_percentage, 0.0F)
        val circleColor = typedArray.getColor(R.styleable.ProgLangView_color, Color.BLACK)

        this.langName = langName
        this.circleColor = circleColor
        this.percentage = percentage

    }

    fun setName(name: String){
        binding.langName.text = name
    }
    */

}