package co.feip.fefu2025.presentation.anime_info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AnimeInfoViewModel(
    private val getAnimeUseCase: GetAnimeUseCase,
    val id: Int
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    val isLoading_public: StateFlow<Boolean> = isLoading

    private val error = MutableStateFlow<String?>(null)
    val error_public: StateFlow<String?> = error

    val anime = mutableStateOf<Anime?>(null)

    init {
        loadAnime()
    }

    private fun loadAnime() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                anime.value = getAnimeUseCase(id)?.copy()
            } catch (e: Exception) {
                error.value = "Ошибка загрузки: ${e.message ?: "Неизвестная ошибка"}"
            } finally {
                isLoading.value = false
            }
        }
    }
}