package com.syntia.rawrgames.ui.uiState

import androidx.compose.runtime.Stable
import com.syntia.rawrgames.ui.model.GameDetail

@Stable
sealed interface GameDetailUiState {
  data class Success(val gameDetail: GameDetail) : GameDetailUiState
  data class Error(val message: String?) : GameDetailUiState
  object Loading : GameDetailUiState
}