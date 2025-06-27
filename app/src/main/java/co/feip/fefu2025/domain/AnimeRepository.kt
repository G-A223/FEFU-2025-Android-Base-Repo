package co.feip.fefu2025.domain

import co.feip.fefu2025.domain.entities.Anime

interface AnimeRepository {

    suspend fun getListOfAnime(): List<Anime>

    suspend fun getAnime(id: Int): Anime?

    suspend fun searchAnime(query: String): List<Anime>

    fun getListOfAnimeWithoutSuspend(): List<Anime>
}