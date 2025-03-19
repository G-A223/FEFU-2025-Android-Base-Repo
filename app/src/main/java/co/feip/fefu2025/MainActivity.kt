package co.feip.fefu2025

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme
import kotlin.random.Random
import co.feip.fefu2025.FlexBoxLayout



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
