package co.feip.fefu2025.dependency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.data.AnimeRepositoryImpl
import co.feip.fefu2025.domain.use_cases.SearchAnimeUseCase
import co.feip.fefu2025.presentation.anime_search.SearchAnimeViewModel

object ProvideSearchAnimeData {
    fun provideSearchViewModel(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = AnimeRepositoryImpl()
                val useCase = SearchAnimeUseCase(repository)
                return SearchAnimeViewModel(useCase) as T
            }
        }
    }
}