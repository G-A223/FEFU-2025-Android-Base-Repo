package co.feip.fefu2025.presentation.anime_info

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.api.ScoreStat
import kotlinx.coroutines.launch
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.GetAnimeRecsUseCase
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import co.feip.fefu2025.domain.use_cases.GetStatsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AnimeInfoViewModel(
    private val getAnimeUseCase: GetAnimeUseCase,
    private val getRecommendedUseCase: GetAnimeRecsUseCase,
    private val getStatsUseCase: GetStatsUseCase,
    private val initialAnime: Anime? = null,
    val id: Int
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    val isLoading_public: StateFlow<Boolean> = isLoading

    private val error = MutableStateFlow<String?>(null)
    val error_public: StateFlow<String?> = error

    val anime = mutableStateOf<Anime?>(null)
    val recommended = mutableStateOf<List<Anime>>(emptyList())
    val ratings =  mutableStateOf<List<ScoreStat>>(emptyList())

    init {
        initialAnime?.let { anime.value = it }

        loadAnime()
        loadRecommendations()
        loadRatings()
    }

    fun retry() {
        loadAnime()
    }

    private fun loadAnime() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                anime.value = getAnimeUseCase(id)?.copy()
                if (anime.value == null) {
                    error.value = "Данные аниме не найдены"
                }
                Log.d("AnimeInfo", "Загружено аниме: ${anime.value?.name}")
            } catch (e: Exception) {
                Log.e("AnimeInfo", "Ошибка", e)
                error.value = "Ошибка загрузки: ${e.message ?: "Неизвестная ошибка"}"
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            try {
                recommended.value = getRecommendedUseCase(id, 1)
                    .take(10)

                Log.d("AnimeInfo Recommendations", "Рекомендации загружены ${recommended.value}")
            } catch (e: Exception) {
                Log.e("AnimeInfo Recommendations", "Ошибка", e)
                error.value = "Ошибка загрузки: ${e.message ?: "Неизвестная ошибка"}"
            }
        }
    }

    private fun loadRatings() {
        viewModelScope.launch {
            try {
                delay(500)
                val stats = getStatsUseCase(id)
                ratings.value = stats.scores?.sortedByDescending { it.score } ?: emptyList()
                Log.d("AnimeStats", "Статистика загрузилась: ${ratings.value}")
            } catch (e: Exception) {
                Log.e("AnimeStats", "Ошибка загрузки статстики", e)
                ratings.value = emptyList()
            }
        }
    }
}