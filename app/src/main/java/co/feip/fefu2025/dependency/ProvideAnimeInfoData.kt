package co.feip.fefu2025.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.data.AnimeRepositoryImpl
import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.use_cases.GetAnimeRecsUseCase
import co.feip.fefu2025.domain.use_cases.GetAnimeUseCase
import co.feip.fefu2025.domain.use_cases.GetStatsUseCase
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel

object ProvideAnimeInfoData {
    fun provideGetStatsUseCase(): GetStatsUseCase {
        return GetStatsUseCase(provideAnimeInfoRepository())
    }

    fun provideAnimeInfoRepository(): AnimeRepository = AnimeRepositoryImpl()

    fun provideGetAnimeInfoUseCase(id: Int): GetAnimeUseCase {
        return GetAnimeUseCase(provideAnimeInfoRepository())
    }

    fun provideRecommendationsUseCase(): GetAnimeRecsUseCase {
        return GetAnimeRecsUseCase(provideAnimeInfoRepository())
    }

    fun provideAnimeInfoViewModel(id: Int): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimeInfoViewModel(
                    provideGetAnimeInfoUseCase(id),
                    provideRecommendationsUseCase(),
                    provideGetStatsUseCase(),
                    id = id
                ) as T
            }
        }
    }
}