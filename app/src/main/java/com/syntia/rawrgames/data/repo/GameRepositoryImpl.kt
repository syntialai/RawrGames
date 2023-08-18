package com.syntia.rawrgames.data.repo

import com.syntia.rawrgames.data.local.GameDao
import com.syntia.rawrgames.data.local.entity.GameEntity
import com.syntia.rawrgames.data.network.CoroutineDispatchers
import com.syntia.rawrgames.data.network.GameService
import com.syntia.rawrgames.data.network.model.GameDetailResponse
import com.syntia.rawrgames.data.network.model.GameListResponse
import com.syntia.rawrgames.domain.model.Result
import com.syntia.rawrgames.domain.util.ResultUtil.asResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GameRepositoryImpl @Inject constructor(
  private val gameService: GameService,
  private val gameDao: GameDao,
  private val coroutineDispatchers: CoroutineDispatchers
) : GameRepository {

  override fun getGames(): Flow<Result<GameListResponse>> {
    return flow { emit(gameService.getGames()) }
      .asResult()
      .flowOn(coroutineDispatchers.IO)
  }

  override fun searchGame(query: String): Flow<Result<GameListResponse>> {
    return flow { emit(gameService.searchGames(query)) }
      .asResult()
      .flowOn(coroutineDispatchers.IO)
  }

  override fun getGameDetail(id: String): Flow<Result<GameDetailResponse>> {
    return flow { emit(gameService.getGameDetail(id)) }
      .asResult()
      .flowOn(coroutineDispatchers.IO)
  }

  override fun getFavoriteGames(): Flow<Result<List<GameEntity>>> {
    return gameDao.getGames()
      .asResult()
      .flowOn(coroutineDispatchers.IO)
  }

  override fun getFavoriteGameById(id: String): Flow<GameEntity?> {
    return gameDao.getGameById(id)
      .flowOn(coroutineDispatchers.IO)
  }

  override suspend fun deleteGame(id: String) {
    gameDao.deleteGame(id)
  }

  override suspend fun addFavorite(gameEntity: GameEntity) {
    gameDao.upsertGame(gameEntity)
  }
}