package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime

class GetAnimeListUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(page: Int): List<Anime> {
        return repository.getListOfAnime(page)
    }

    fun getWithoutSuspend(): List<Anime> {
        return repository.getListOfAnimeWithoutSuspend()
    }
}