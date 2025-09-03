package com.syntia.rawrgames.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syntia.rawrgames.R
import com.syntia.rawrgames.ui.component.GameCards
import com.syntia.rawrgames.ui.component.SearchBox
import com.syntia.rawrgames.ui.model.Game
import com.syntia.rawrgames.ui.theme.RawrGamesTheme
import com.syntia.rawrgames.ui.uiState.GamesUiState

@Composable
fun HomeScreen(
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit,
  viewModel: HomeViewModel = hiltViewModel()
) {
  val gamesUiState by viewModel.gamesUiState.collectAsStateWithLifecycle()
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

  HomeScreen(
    gamesUiState = gamesUiState,
    searchQuery = searchQuery,
    onSearchQueryChanged = viewModel::onSearchQueryChanged,
    goToGameDetail = goToGameDetail,
    showSnackbar = showSnackbar
  )
}

@Composable
fun HomeScreen(
  gamesUiState: GamesUiState,
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(16.dp))

    SearchBox(
      query = searchQuery,
      onQueryChanged = onSearchQueryChanged,
      placeholder = stringResource(R.string.search_games)
    )

    Spacer(modifier = Modifier.height(24.dp))

    GameCards(
      gamesUiState = gamesUiState,
      showSnackbar = showSnackbar,
      onClick = goToGameDetail
    )
  }
}

@Composable
@Preview(showBackground = true, device = Devices.NEXUS_6)
fun GameDetailScreenPreview() {
  val game = Game(
    id = "1213321321",
    name = "Vampire: The Masquerade - Bloodlines 2",
    releaseDate = "2021-08-20",
    imageUrl = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
    rating = 4.5f
  )

  RawrGamesTheme {
    HomeScreen(
      GamesUiState.Success(listOf(game)),
      "",
      {},
      {},
      {}
    )
  }
}