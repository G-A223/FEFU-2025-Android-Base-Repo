package co.feip.fefu2025.domain

import co.feip.fefu2025.domain.entities.Anime

interface AnimeRepository {

    suspend fun getListOfAnime(page: Int): List<Anime>

    suspend fun getAnime(id: Int): Anime?

    suspend fun searchAnime(query: String, page: Int): List<Anime>
}