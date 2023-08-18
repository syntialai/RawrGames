package com.syntia.rawrgames.domain.util

import com.syntia.rawrgames.data.local.entity.GameEntity
import com.syntia.rawrgames.data.network.model.GameDetailResponse
import com.syntia.rawrgames.data.network.model.GameResponse
import com.syntia.rawrgames.domain.model.Result
import com.syntia.rawrgames.ui.model.Game
import com.syntia.rawrgames.ui.model.GameDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

object ResultUtil {

  fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
      .map<T, Result<T>> { Result.Success(it) }
      .onStart { emit(Result.Loading) }
      .catch { emit(Result.Error(it)) }
  }

  fun mapToGameUiModels(responses: List<GameResponse>?) = responses.orEmpty().map {
    with(it) {
      Game(
        id = id.orEmpty(),
        name = name ?: "-",
        releaseDate = released.orEmpty(),
        imageUrl = backgroundImage.orEmpty(),
        rating = rating ?: 0.0f
      )
    }
  }

  fun mapToGameDetailUiModels(response: GameDetailResponse) = with(response) {
    GameDetail(
      id = id.orEmpty(),
      name = name ?: "-",
      releaseDate = released.orEmpty(),
      imageUrl = backgroundImage.orEmpty(),
      rating = rating ?: 0.0f,
      description = descriptionRaw.orEmpty(),
      playedCount = added ?: 0L,
      publisher = publishers?.firstOrNull()?.name.orEmpty()
    )
  }

  fun mapEntitiesToGameUiModels(entities: List<GameEntity>) = entities.map {
    with(it) {
      Game(
        id = id,
        name = name,
        releaseDate = releaseDate,
        imageUrl = imageUrl,
        rating = rating
      )
    }
  }

  fun mapToGameEntity(gameDetail: GameDetail) = GameEntity(
    id = gameDetail.id,
    name = gameDetail.name,
    releaseDate = gameDetail.releaseDate,
    imageUrl = gameDetail.imageUrl,
    rating = gameDetail.rating
  )
}