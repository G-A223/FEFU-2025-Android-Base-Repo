package co.feip.fefu2025.domain.use_cases

import co.feip.fefu2025.data.api.Statistics
import co.feip.fefu2025.domain.AnimeRepository

class GetStatsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Statistics {
        return repository.getAnimeStats(id)
    }
}