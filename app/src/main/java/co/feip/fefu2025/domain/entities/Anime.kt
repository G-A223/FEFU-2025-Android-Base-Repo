package co.feip.fefu2025.domain.entities

data class Anime(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: String,
    val genres: List<String>,
    val description: String,
    val year: String,
    val episodes: Int
)
