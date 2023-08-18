package com.syntia.rawrgames.ui.uiState

import androidx.compose.runtime.Stable
import com.syntia.rawrgames.ui.model.Game

@Stable
sealed interface GamesUiState {
  data class Success(val games: List<Game>) : GamesUiState
  data class Error(val message: String?) : GamesUiState
  object Loading : GamesUiState
}