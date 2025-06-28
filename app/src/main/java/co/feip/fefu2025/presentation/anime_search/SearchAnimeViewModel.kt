package co.feip.fefu2025.presentation.anime_search

import androidx.lifecycle.ViewModel
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchAnimeViewModel(private val searchAnimeUseCase: SearchAnimeUseCase): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<Anime>>(emptyList())
    val searchResults: StateFlow<List<Anime>> = _searchResults

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        if (query.isNotEmpty()) {
            _isSearching.value = true
            _error.value = null
            searchJob = viewModelScope.launch {
                try {
                    _searchResults.value = searchAnimeUseCase(query, 1)
                } catch (e: Exception) {
                    _error.value = "Ошибка поиска: ${e.message ?: "Неизвестная ошибка"}"
                    _searchResults.value = emptyList()
                } finally {
                    _isSearching.value = false
                }
            }
        } else {
            _searchResults.value = emptyList()
        }
    }
}