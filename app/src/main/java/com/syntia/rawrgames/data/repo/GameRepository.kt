package com.syntia.rawrgames.data.repo

import com.syntia.rawrgames.data.local.entity.GameEntity
import com.syntia.rawrgames.data.network.model.GameDetailResponse
import com.syntia.rawrgames.data.network.model.GameListResponse
import com.syntia.rawrgames.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface GameRepository {

  fun getGames(): Flow<Result<GameListResponse>>

  fun searchGame(query: String): Flow<Result<GameListResponse>>

  fun getGameDetail(id: String): Flow<Result<GameDetailResponse>>

  fun getFavoriteGames(): Flow<Result<List<GameEntity>>>

  fun getFavoriteGameById(id: String): Flow<GameEntity?>

  suspend fun deleteGame(id: String)

  suspend fun addFavorite(gameEntity: GameEntity)
}