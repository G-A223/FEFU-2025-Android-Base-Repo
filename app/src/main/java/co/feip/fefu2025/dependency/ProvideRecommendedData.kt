package co.feip.fefu2025.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.data.AnimeRepositoryImpl
import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.use_cases.GetAnimeListUseCase
import co.feip.fefu2025.presentation.anime_list.RecommendedViewModel

object ProvideRecommendedData {
    fun provideAnimeListRepository(): AnimeRepository = AnimeRepositoryImpl()

    fun provideGetAnimeListUseCase(): GetAnimeListUseCase {
        return GetAnimeListUseCase(provideAnimeListRepository())
    }

    fun provideAnimeListViewModel(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecommendedViewModel(provideGetAnimeListUseCase()) as T
            }
        }
    }
}