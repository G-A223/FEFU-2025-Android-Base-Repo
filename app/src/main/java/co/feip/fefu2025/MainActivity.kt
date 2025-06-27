package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.presentation.anime_list.AnimeList
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val viewModel: AnimeListViewModel = viewModel(
        factory = ProvideAnimeListData.provideAnimeListViewModel()
    )
     AnimeList(viewModel)
}

