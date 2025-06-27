package co.feip.fefu2025.domain.entities

data class Anime(
    val name: String,
    val imageRes: Int,
    val rating: String,
    val genres: List<String>,
    val description: String,
    val year: String,
    val episodes: Int
)
