package co.feip.fefu2025

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    private val langs = listOf(
        "Java",
        "Python",
        "Kotlin",
        "C++",
        "C#",
        "PHP",
        "Go",
        "Scala",
        "R",
        "SQL",
        "Swift",
        "C",
        "Delphi",
        "Haskell",
        "Perl",
        "Lua",
        "Ruby",
        "Rust"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val addButton = findViewById<Button>(R.id.btnAdd)
        val flexBoxLayout = findViewById<FlexBoxLayout>(R.id.flexBox)

        addButton.setOnClickListener {
            val newLanguageView = ProgLangView(this).apply {
                setName(langs.random())
                setPercent(Random.nextFloat() * 100)
                setColor(android.graphics.Color.argb(
                    255,
                    Random.nextInt(256),
                    Random.nextInt(256),
                    Random.nextInt(256)
                ))
            }
            flexBoxLayout.addView(newLanguageView)
        }
    }
}
