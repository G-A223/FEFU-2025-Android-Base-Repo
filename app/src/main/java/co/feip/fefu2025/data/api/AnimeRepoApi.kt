package co.feip.fefu2025.data.api

data class AnimeRepoApi (
    val data: List<AnimeData>,
    val pagination: Pagination
)

data class AnimeData(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages,
    val score: Double?,
    val genres: List<Genre>,
    val synopsis: String?,
    val year: Int?,
    val episodes: Int?
)

data class AnimeImages(
    val jpg: ImageUrls
)

data class ImageUrls(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)

data class Genre(
    val name: String
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean
)