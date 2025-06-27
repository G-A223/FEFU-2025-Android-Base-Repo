package co.feip.fefu2025.domain

import co.feip.fefu2025.domain.entities.Anime

interface AnimeRepository {

    fun getListOfAnime(): List<Anime>

    fun getAnime(id: Int): Anime?
}