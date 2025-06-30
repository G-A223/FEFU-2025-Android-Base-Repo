package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime

class SearchAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String, page: Int): List<Anime> {
        return repository.searchAnime(query, page)
    }
}