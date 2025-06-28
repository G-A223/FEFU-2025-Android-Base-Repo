package co.feip.fefu2025.data

import android.util.Log
import co.feip.fefu2025.data.api.AnimeData
import co.feip.fefu2025.data.api.ApiClient
import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime


class AnimeRepositoryImpl: AnimeRepository {
    private val apiService = ApiClient.apiService

    suspend override fun getListOfAnime(page: Int): List<Anime> {
        val data = apiService.getTopAnime(page)

        return data.data.map { it.toDomainAnime() }
    }

    suspend override fun getAnime(id: Int): Anime? {
        return try {
            Log.d("API_RESPONSE", "Данные для аниме $id: ${apiService.getAnime(id)}")
            apiService.getAnime(id).data.toDomainAnime()
        } catch(e: Exception) {
            Log.e("API_ERROR", "Не удалось загрузить аниме $id", e)
            null
        }
    }

    override suspend fun searchAnime(query: String, page: Int): List<Anime> {
        val data = apiService.searchAnime(query, page)

        return data.data.map { it.toDomainAnime() }
    }
}

private fun AnimeData.toDomainAnime(): Anime {
    val imageUrl = when {
        images?.jpg?.large_image_url != null -> images.jpg.large_image_url
        images?.jpg?.image_url != null -> images.jpg.image_url
        else -> "" // или URL заглушки
    }

    return Anime(
        id = mal_id,
        name = title,
        imageUrl = imageUrl,
        rating = score?.toString() ?: "N/A",
        genres = genres.map { it.name },
        description = synopsis ?: "No description",
        year = year?.toString() ?: "Unknown",
        episodes = episodes ?: 0
    )
}


