package co.feip.fefu2025.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.feip.fefu2025.RecommendedScreen
import co.feip.fefu2025.presentation.anime_info.AnimeInfoScreen
import co.feip.fefu2025.dependency.ProvideAnimeInfoData
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel
import co.feip.fefu2025.presentation.anime_list.AnimeList
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.Home.route,
        modifier = modifier
    ) {
        composable(Destination.Home.route) {
            val viewModel: AnimeListViewModel = viewModel(
                factory = ProvideAnimeListData.provideAnimeListViewModel()
            )
            AnimeList(
                viewModel = viewModel,
                onAnimeClick = { animeId ->
                    navHostController.navigate(Destination.AnimeInfo(animeId).route)
                }
            )
        }

        composable(
            route = Destination.AnimeInfo.ROUTE_WITH_PLACEHOLDER,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = Destination.AnimeInfo.getId(backStackEntry)
            val viewModel: AnimeInfoViewModel = viewModel(
                factory = ProvideAnimeInfoData.provideAnimeInfoViewModel(animeId)
            )
            AnimeInfoScreen(
                viewModel = viewModel,
                onBackClick = { navHostController.popBackStack() },
                navController = navHostController
            )
        }

        composable(Destination.Recommended.route) {
            RecommendedScreen(
                onBackClick = { navHostController.popBackStack() },
                onAnimeClick = { animeId ->
                    navHostController.navigate(Destination.AnimeInfo(animeId).route)
                }
            )
        }
    }
}

