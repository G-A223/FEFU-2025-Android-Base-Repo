package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime

class GetAnimeRecsUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(id: Int, page: Int): List<Anime> {
        return repository.getAnimeRecs(id, page)
    }
}