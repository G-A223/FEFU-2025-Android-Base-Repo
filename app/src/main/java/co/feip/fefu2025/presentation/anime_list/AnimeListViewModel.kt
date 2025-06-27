package co.feip.fefu2025.presentation.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeListUseCase


class AnimeListViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {

    val animeList = MutableStateFlow<List<Anime>>(emptyList())

    init {
        extractAnimeList()
    }

    private fun extractAnimeList() {
        viewModelScope.launch {
            animeList.value = getAnimeListUseCase()
        }
    }
}