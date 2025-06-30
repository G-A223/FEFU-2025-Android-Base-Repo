package co.feip.fefu2025.data.api

data class AnimeRepoApi (
    val data: List<AnimeData>,
    val pagination: Pagination
)

data class AnimeSingleRepoApi(
    val data: AnimeData
)

data class AnimeData(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages?,
    val score: Double?,
    val genres: List<Genre>,
    val synopsis: String?,
    val year: Int?,
    val episodes: Int?
)

data class AnimeRecommendations(
    val data: List<Recommendation>
)

data class Recommendation(
    val entry: RecommendedAnime,
    val votes: Int?
)

data class RecommendedAnime(
    val mal_id: Int,
    val title: String,
    val images: AnimeImages?,
    val score: Double?,
    val genres: List<Genre>?,
    val synopsis: String?,
    val year: Int?,
    val episodes: Int?
)


data class AnimeImages(
    val jpg: ImageUrls?
)

data class ImageUrls(
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?
)

data class Genre(
    val name: String
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean
)

data class AnimeStats(
    val data: Statistics
)

data class Statistics(
    val scores: List<ScoreStat>?,
    val total: Int?
)

data class ScoreStat(
    val score: Int,
    val votes: Int,
    val percentage: Double
)
