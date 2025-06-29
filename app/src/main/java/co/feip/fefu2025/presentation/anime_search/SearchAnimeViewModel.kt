package co.feip.fefu2025.presentation.anime_search

import androidx.lifecycle.ViewModel
import co.feip.fefu2025.domain.entities.Anime
import co.feip.fefu2025.domain.use_cases.SearchAnimeUseCase
import kotlinx.coroutines.Job
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
    private var currentPage = 1
    private var hasNextPage = true

    fun loadMoreResults() {
        if (!_isSearching.value && hasNextPage) {
            currentPage++
            performSearch()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        currentPage = 1
        hasNextPage = true

        if (query.isNotEmpty()) {
            performSearch()
        } else {
            _searchResults.value = emptyList()
        }
    }

    private fun performSearch() {
        _isSearching.value = true
        _error.value = null
        searchJob = viewModelScope.launch {
            try {
                val res = searchAnimeUseCase(_searchQuery.value, currentPage)

                _searchResults.value = if (currentPage == 1) {
                    res
                } else {
                    _searchResults.value + res
                }

                hasNextPage = res.isEmpty()
            } catch (e: Exception) {
                _error.value = "Ошибка поиска: ${e.message ?: "Неизвестная ошибка"}"

                if (currentPage > 1)
                    currentPage--
            } finally {
                _isSearching.value = false
            }
        }
    }
}