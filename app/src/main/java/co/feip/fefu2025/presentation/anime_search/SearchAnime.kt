package co.feip.fefu2025.presentation.anime_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.EmptySearchView
import co.feip.fefu2025.ErrorView
import co.feip.fefu2025.LoadingView
import co.feip.fefu2025.NoResultsView
import co.feip.fefu2025.dependency.ProvideSearchAnimeData
import co.feip.fefu2025.domain.entities.Anime
import coil.compose.AsyncImage

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    query: String = "",
) {
    val viewModel: SearchAnimeViewModel = viewModel(factory = ProvideSearchAnimeData.provideSearchViewModel())
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            viewModel.onSearchQueryChanged(query)
        }
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) },
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isSearching -> LoadingView()
                error != null -> ErrorView(message = error!!, onRetry = {
                    viewModel.onSearchQueryChanged(searchQuery)
                })
                searchQuery.isEmpty() -> EmptySearchView()
                searchResults.isEmpty() -> NoResultsView()
                else -> SearchResultsList(results = searchResults, onAnimeClick = onAnimeClick)
            }
        }
    }
}

@Composable
fun SearchResultsList(results: List<Anime>, onAnimeClick: (Int) -> Unit) {
    LazyColumn {
        items(results) { anime ->
            AnimeSearchItem(anime = anime, onClick = { onAnimeClick(anime.id) })
        }
    }
}

@Composable
fun AnimeSearchItem(anime: Anime, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = anime.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                    .size(64.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = anime.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = anime.genres.joinToString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Поиск аниме...") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.Transparent
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { }
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}