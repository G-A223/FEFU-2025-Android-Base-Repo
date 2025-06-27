package co.feip.fefu2025.navigation

import androidx.navigation.NavBackStackEntry

sealed class Destination(val route: String) {
    data object Home: Destination("home")

    data class AnimeInfo(val id: Int) : Destination("anime_info/$id") {
        companion object {
            const val ROUTE_WITH_PLACEHOLDER = "anime_info/{id}"

            fun getId(backStackEntry: NavBackStackEntry): Int {
                return backStackEntry.arguments?.getInt("id") ?: 0
            }
        }
    }

    data object Recommended : Destination("recommended")
}