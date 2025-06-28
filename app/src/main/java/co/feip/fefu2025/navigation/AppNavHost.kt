package co.feip.fefu2025.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.feip.fefu2025.presentation.anime_rec.RecommendedScreen
import co.feip.fefu2025.presentation.anime_info.AnimeInfoScreen
import co.feip.fefu2025.dependency.ProvideAnimeInfoData
import co.feip.fefu2025.dependency.ProvideAnimeListData
import co.feip.fefu2025.presentation.anime_info.AnimeInfoViewModel
import co.feip.fefu2025.presentation.anime_list.AnimeList
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel
import co.feip.fefu2025.presentation.anime_search.SearchScreen

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
                    val cachedAnime = viewModel.animeList.value.find { it.id == animeId }

                    navHostController.currentBackStackEntry?.savedStateHandle?.set("anime_id", animeId)

                    navHostController.navigate(
                        Destination.AnimeInfo(animeId).route
                    )
                },
                navController = navHostController
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

        composable(
            route = Destination.Search.route,
            arguments = listOf(
                navArgument("query") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query") ?: ""

            SearchScreen(
                query = query,
                onBackClick = { navHostController.popBackStack() },
                onAnimeClick = { animeId ->
                    navHostController.navigate(Destination.AnimeInfo(animeId).route)
                }
            )
        }
    }
}

