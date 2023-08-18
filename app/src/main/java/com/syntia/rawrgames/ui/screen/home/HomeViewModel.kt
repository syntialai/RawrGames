package com.syntia.rawrgames.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntia.rawrgames.data.repo.GameRepository
import com.syntia.rawrgames.domain.model.Result
import com.syntia.rawrgames.domain.util.ResultUtil
import com.syntia.rawrgames.ui.uiState.GamesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

  val gamesUiState: StateFlow<GamesUiState> = gamesUiState()
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = GamesUiState.Loading,
    )

  fun onSearchQueryChanged(query: String) {
    savedStateHandle[SEARCH_QUERY] = query
  }

  private fun gamesUiState() = searchQuery.flatMapLatest { query ->
    when {
      query.isBlank() -> getGames()
      query.length > 3 -> searchGames(query)
      else -> gamesUiState
    }
  }

  private fun searchGames(query: String) = gameRepository.searchGame(query)
    .map { result ->
      when (result) {
        is Result.Success -> {
          val games = ResultUtil.mapToGameUiModels(result.data.results)
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

  private fun getGames() = gameRepository.getGames()
    .map { result ->
      when (result) {
        is Result.Success -> {
          val games = ResultUtil.mapToGameUiModels(result.data.results)
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

private const val SEARCH_QUERY = "searchQuery"