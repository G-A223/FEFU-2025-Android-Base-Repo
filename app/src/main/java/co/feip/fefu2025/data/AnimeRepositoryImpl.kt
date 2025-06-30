package co.feip.fefu2025.data

import android.util.Log
import co.feip.fefu2025.data.api.AnimeData
import co.feip.fefu2025.data.api.Statistics
import co.feip.fefu2025.data.api.ApiClient
import co.feip.fefu2025.data.api.RecommendedAnime
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

    suspend override fun searchAnime(query: String, page: Int): List<Anime> {
        val data = apiService.searchAnime(query, page)

        return data.data.map { it.toDomainAnime() }
    }

    suspend override fun getAnimeRecs(id: Int, page: Int): List<Anime> {
        val data = apiService.getAnimeRecs(id, page)

        Log.d("API_RESPONSE", "Рекоммендации: ${data.data}")
        return data.data.map { it.entry.toDomainAnime() }
    }

    suspend override fun getAnimeStats(id: Int): Statistics {
        return apiService.getAnimeStats(id).data
    }
}

private fun AnimeData.toDomainAnime(): Anime {
    val imageUrl = when {
        images?.jpg?.large_image_url != null -> images.jpg.large_image_url
        images?.jpg?.image_url != null -> images.jpg.image_url
        else -> ""
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

private fun RecommendedAnime.toDomainAnime(): Anime {
    val imageUrl = when {
        images?.jpg?.large_image_url != null -> images.jpg.large_image_url
        images?.jpg?.image_url != null -> images.jpg.image_url
        else -> ""
    }

    return Anime(
        id = mal_id,
        name = title,
        imageUrl = imageUrl,
        rating = score?.toString() ?: "N/A",
        genres = genres?.map { it.name } ?: emptyList(),
        description = synopsis ?: "No description",
        year = year?.toString() ?: "Unknown",
        episodes = episodes ?: 0
    )
}


