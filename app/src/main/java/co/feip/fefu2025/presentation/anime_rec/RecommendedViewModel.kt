package co.feip.fefu2025.presentation.anime_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeRecsUseCase
import kotlinx.coroutines.flow.StateFlow


class RecommendedViewModel(
    private val getAnimeRecsUseCase: GetAnimeRecsUseCase,
    val id: Int
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    val isLoading_public: StateFlow<Boolean> = isLoading

    private val error = MutableStateFlow<String?>(null)
    val error_public: StateFlow<String?> = error

    val animeList = MutableStateFlow<List<Anime>>(emptyList())

    private var currentPage = 1
    private var hasNextPage = true


    init {
        loadAnimeList()
    }

    fun retry() {
        loadAnimeList()
    }


    private fun loadAnimeList() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                val newAnimeRecs = getAnimeRecsUseCase(id, currentPage)
                animeList.value = animeList.value + newAnimeRecs
                currentPage++
                hasNextPage = newAnimeRecs.isNotEmpty()
                Log.d("RecommendedScreen", "Рекомендации загружены")
            } catch (e: Exception) {
                error.value = "Ошибка загрузки: ${e.message ?: "Неизвестная ошибка"}"

                if (currentPage > 1)
                    currentPage--
            } finally {
                isLoading.value = false
            }
        }
    }
}