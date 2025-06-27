package co.feip.fefu2025.presentation.anime_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import co.feip.fefu2025.AnimeInfo
import co.feip.fefu2025.AnimeRec
import co.feip.fefu2025.ErrorView
import co.feip.fefu2025.LoadingView
import co.feip.fefu2025.RatingChart
import co.feip.fefu2025.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeInfoScreen(
    viewModel: AnimeInfoViewModel,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    val votes = listOf(
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
        (100..1000).random(),
    )

    val animeData = viewModel.anime.value
    val isLoading by viewModel.isLoading_public.collectAsState()
    val error by viewModel.error_public.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black,
                ),
                title = {
                    if (!isLoading || error == null) {
                        Text("Загрузка...")
                    } else {
                        Text(text = animeData?.name.toString())
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { paddings ->
        when {
            isLoading -> LoadingView()
            error != null -> ErrorView(
                message = error!!,
                onRetry = { }
            )

            else -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding((paddings))
                ) {
                    item {
                        AnimeInfo(viewModel)
                    }

                    item {
                        RatingChart(votes)
                    }

                    item {
                        AnimeRec(
                            onAnimeClick = { animeId ->
                                navController.navigate(Destination.AnimeInfo(animeId).route)
                            },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
