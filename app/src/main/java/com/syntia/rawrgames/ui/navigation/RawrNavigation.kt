package com.syntia.rawrgames.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.syntia.rawrgames.ui.screen.detail.GameDetailScreen
import com.syntia.rawrgames.ui.screen.favorite.FavoriteScreen
import com.syntia.rawrgames.ui.screen.home.HomeScreen

/**
 * Navigation
 */
fun NavController.navigateToSingleTop(route: String) {
  this.navigate(route) {
    popUpTo(graph.findStartDestination().id) {
      saveState = true
    }
    launchSingleTop = true
    restoreState = true

  }
}

fun NavController.navigateToHome() {
  this.navigateToSingleTop(RawrRoute.HOME)
}

fun NavController.navigateToFavorite() {
  this.navigateToSingleTop(RawrRoute.FAVORITE)
}

fun NavController.navigateToGameDetail(gameId: String, navOptions: NavOptions? = null) {
  this.navigate(
    RawrRoute.constructRouteWithParam(RawrRoute.GAME_DETAIL, Pair(RawrArgs.ID, gameId)),
    navOptions
  )
}

/**
 * Builders
 */
fun NavGraphBuilder.homeScreen(
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit
) {
  composable(route = RawrRoute.HOME) {
    HomeScreen(
      showSnackbar = showSnackbar,
      goToGameDetail = goToGameDetail
    )
  }
}

fun NavGraphBuilder.favoriteScreen(
  showSnackbar: (String?) -> Unit,
  goToGameDetail: (String) -> Unit
) {
  composable(route = RawrRoute.FAVORITE) {
    FavoriteScreen(
      showSnackbar = showSnackbar,
      goToGameDetail = goToGameDetail
    )
  }
}

fun NavGraphBuilder.gameDetailScreen(
  showSnackbar: (String?) -> Unit
) {
  composable(
    route = RawrRoute.GAME_DETAIL,
    arguments = listOf(
      navArgument(RawrArgs.ID) { type = NavType.StringType }
    )
  ) {
    GameDetailScreen(showSnackbar = showSnackbar)
  }
}



