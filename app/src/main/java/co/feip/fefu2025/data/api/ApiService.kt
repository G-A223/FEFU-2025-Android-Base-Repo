package co.feip.fefu2025.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    @GET("v4/top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): AnimeRepoApi

    @GET("v4/anime/{id}")
    suspend fun getAnime(
        @Path("id") id: Int
    ): AnimeSingleRepoApi

    @GET("v4/anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): AnimeRepoApi

    @GET("v4/anime/{id}/recommendations")
    suspend fun getAnimeRecs(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): AnimeRecommendations

    @GET("v4/anime/{id}/statistics")
    suspend fun getAnimeStats(
        @Path("id") id: Int
    ): AnimeStats
}