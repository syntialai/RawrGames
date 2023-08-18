package com.syntia.rawrgames.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.syntia.rawrgames.ui.screen.main.MainAppState

@Composable
fun RawrNavHost(
    appState: MainAppState,
    modifier: Modifier = Modifier,
    startDestination: String = RawrRoute.HOME
) {
  val navController = appState.navController

  NavHost(
      navController = navController,
      startDestination = startDestination,
      modifier = modifier
  ) {
    homeScreen(
      appState::showSnackbar,
      navController::navigateToGameDetail
    )
    favoriteScreen(
      appState::showSnackbar,
      navController::navigateToGameDetail
    )
    gameDetailScreen(appState::showSnackbar)
  }
}