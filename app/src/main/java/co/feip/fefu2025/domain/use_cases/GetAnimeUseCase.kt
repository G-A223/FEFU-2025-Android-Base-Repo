package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime

class GetAnimeUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(id: Int): Anime? {
        return repository.getAnime(id)
    }
}