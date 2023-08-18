package com.syntia.rawrgames.data.network

import com.syntia.rawrgames.data.network.model.GameDetailResponse
import com.syntia.rawrgames.data.network.model.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

  @GET("games")
  suspend fun getGames(): GameListResponse

  @GET("games")
  suspend fun searchGames(
    @Query("search") searchQuery: String
  ): GameListResponse

  @GET("games/{id}")
  suspend fun getGameDetail(@Path("id") id: String): GameDetailResponse
}