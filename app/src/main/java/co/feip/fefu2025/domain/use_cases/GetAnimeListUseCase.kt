package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.entities.Anime

class GetAnimeListUseCase(private val repository: AnimeRepository) {

    operator fun invoke(): List<Anime> {
        return repository.getListOfAnime()
    }
}