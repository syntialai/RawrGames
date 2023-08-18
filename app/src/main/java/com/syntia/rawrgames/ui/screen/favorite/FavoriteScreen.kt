package com.syntia.rawrgames.ui.screen.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.syntia.rawrgames.ui.component.GameCards
import com.syntia.rawrgames.ui.model.Game
import com.syntia.rawrgames.ui.theme.RawrGamesTheme
import com.syntia.rawrgames.ui.uiState.GamesUiState

@Composable
fun FavoriteScreen(
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit,
  viewModel: FavoriteViewModel = hiltViewModel(),
) {
  val gamesUiState by viewModel.favoriteGamesUiState.collectAsStateWithLifecycle()

  FavoriteScreen(
    gamesUiState = gamesUiState,
    showSnackbar = showSnackbar,
    goToGameDetail = goToGameDetail)
}

@Composable
fun FavoriteScreen(
  gamesUiState: GamesUiState,
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit
) {
  GameCards(
    gamesUiState = gamesUiState,
    showSnackbar = showSnackbar,
    onClick = goToGameDetail,
    modifier = Modifier.padding(horizontal = 16.dp),
    verticalSpacer = true
  )
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
//    FavoriteScreen()
  }
}