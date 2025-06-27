package co.feip.fefu2025.presentation.anime_info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase


class AnimeInfoViewModel(
    private val getAnimeUseCase: GetAnimeUseCase,
    val id: Int
) : ViewModel() {

    val anime = mutableStateOf<Anime?>(null)

    init {
        extractAnime()
    }

    private fun extractAnime() {
        viewModelScope.launch {
            anime.value = getAnimeUseCase(id)?.copy()
        }
    }
}