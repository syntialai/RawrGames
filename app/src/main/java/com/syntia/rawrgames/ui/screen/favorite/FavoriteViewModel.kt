package com.syntia.rawrgames.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntia.rawrgames.data.repo.GameRepository
import com.syntia.rawrgames.domain.model.Result
import com.syntia.rawrgames.domain.util.ResultUtil
import com.syntia.rawrgames.ui.uiState.GamesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FavoriteViewModel @Inject constructor(
  private val gameRepository: GameRepository
) : ViewModel() {

  val favoriteGamesUiState: StateFlow<GamesUiState> = gamesUiState()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = GamesUiState.Loading,
    )

  private fun gamesUiState() = gameRepository.getFavoriteGames()
    .map { result ->
      when (result) {
        is Result.Success -> {
          val games = ResultUtil.mapEntitiesToGameUiModels(result.data)
          GamesUiState.Success(games)
        }

        is Result.Loading -> {
          GamesUiState.Loading
        }

        is Result.Error -> {
          GamesUiState.Error(result.exception?.message)
        }
      }
    }
}