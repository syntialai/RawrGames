package com.syntia.rawrgames.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntia.rawrgames.data.repo.GameRepository
import com.syntia.rawrgames.domain.model.Result
import com.syntia.rawrgames.domain.util.ResultUtil
import com.syntia.rawrgames.ui.model.GameDetail
import com.syntia.rawrgames.ui.navigation.RawrArgs
import com.syntia.rawrgames.ui.uiState.GameDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GameDetailViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val gameId: String
    get() = savedStateHandle.get<String>(RawrArgs.ID).orEmpty()

  val gameDetailUiState: StateFlow<GameDetailUiState> = gameDetailUiState()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = GameDetailUiState.Loading,
    )

  val isFavorite: StateFlow<Boolean> = gameRepository.getFavoriteGameById(gameId)
    .map { it != null }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = false,
    )

  fun updateFavorite(value: Boolean, gameDetail: GameDetail) {
    viewModelScope.launch {
      if (value) {
        gameRepository.addFavorite(ResultUtil.mapToGameEntity(gameDetail))
      } else {
        gameRepository.deleteGame(gameId)
      }
    }
  }

  private fun gameDetailUiState() = gameRepository.getGameDetail(gameId)
    .map { result ->
      when (result) {
        is Result.Success -> {
          val gameDetail = ResultUtil.mapToGameDetailUiModels(result.data)
          GameDetailUiState.Success(gameDetail)
        }

        is Result.Loading -> {
          GameDetailUiState.Loading
        }

        is Result.Error -> {
          GameDetailUiState.Error(result.exception?.message)
        }
      }
    }
}