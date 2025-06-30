package co.feip.fefu2025.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.data.AnimeRepositoryImpl
import co.feip.fefu2025.domain.AnimeRepository
import co.feip.fefu2025.domain.use_cases.GetAnimeRecsUseCase
import co.feip.fefu2025.presentation.anime_list.RecommendedViewModel

object ProvideRecommendedData {
    fun provideAnimeRecsRepository(): AnimeRepository = AnimeRepositoryImpl()

    fun provideGetAnimeRecsUseCase(): GetAnimeRecsUseCase {
        return GetAnimeRecsUseCase(provideAnimeRecsRepository())
    }

    fun provideAnimeRecsViewModel(id: Int): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecommendedViewModel(
                    provideGetAnimeRecsUseCase(),
                    id
                ) as T
            }
        }
    }
}