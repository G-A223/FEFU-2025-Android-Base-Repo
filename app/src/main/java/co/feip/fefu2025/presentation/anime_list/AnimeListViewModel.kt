package co.feip.fefu2025.presentation.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeListUseCase
import kotlinx.coroutines.flow.StateFlow


class AnimeListViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {
    private val isLoading = MutableStateFlow(false)
    val isLoading_public: StateFlow<Boolean> = isLoading

    private val error = MutableStateFlow<String?>(null)
    val error_public: StateFlow<String?> = error

    private var currentPage = 1
    private var hasNextPage = true

    val animeList = MutableStateFlow<List<Anime>>(emptyList())

    init {
        loadAnimeList()
    }

    fun retry() {
        loadAnimeList()
    }

    fun loadMore() {
        if (!isLoading_public.value && hasNextPage) {
            loadAnimeList()
        }
    }

    private fun loadAnimeList() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                val newAnimeList = getAnimeListUseCase(currentPage)
                animeList.value = animeList.value + newAnimeList
                currentPage++
                hasNextPage = newAnimeList.isNotEmpty()
            } catch (e: Exception) {
                error.value = "Ошибка загрузки: ${e.message ?: "Неизвестная ошибка"}"
            } finally {
                isLoading.value = false
            }
        }
    }
}