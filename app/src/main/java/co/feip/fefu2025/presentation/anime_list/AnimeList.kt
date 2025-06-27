package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import co.feip.fefu2025.AnimeCard
import co.feip.fefu2025.ErrorView
import co.feip.fefu2025.LoadingView
import co.feip.fefu2025.R
import co.feip.fefu2025.navigation.Destination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeList(
    viewModel: AnimeListViewModel,
    onAnimeClick: (Int) -> Unit,
    navController: NavHostController
) {
    val animeData by viewModel.animeList.collectAsState()
    val isLoading by viewModel.isLoading_public.collectAsState()
    val error by viewModel.error_public.collectAsState()

    val rows = remember(animeData) {
        animeData.chunked(2)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                ),
                title = {
                    if (error == null) {
                        Search(onSearch = { query ->
                            navController.navigate("search?query=$query")
                        })
                    }
                }
            )
        },
    ) { paddings ->
        when {
            isLoading -> LoadingView()
            error != null -> ErrorView(
                message = error!!,
                onRetry = { viewModel.retry() }
            )

            animeData.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Нет данных для отображения")
                }

            }

            else -> {
                Column(modifier = Modifier.padding(paddings)) {
                    LazyColumn {
                        items(rows) { pair ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                pair.forEach { anime ->
                                    AnimeCard(
                                        name = anime.name,
                                        imageRes = anime.imageRes,
                                        rating = anime.rating,
                                        genres = anime.genres,
                                        onClick = { onAnimeClick(anime.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(onSearch: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        placeholder = { Text("Поиск...") },
        trailingIcon = { IconButton(
                onClick = {
                    onSearch(textState.value.text)
                }
            ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.DarkGray
            )
            }
        },
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            containerColor = Color(0xFFE5E5E5)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp)
    )
}
