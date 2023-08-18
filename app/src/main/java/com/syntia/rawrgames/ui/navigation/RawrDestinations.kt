package com.syntia.rawrgames.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.syntia.rawrgames.R

data class RawrDestination(
  val route: String,
  val icon: ImageVector? = null,
  val iconTextId: Int? = null,
  val navTitleId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
  RawrDestination(
    route = RawrRoute.HOME,
    icon = Icons.Outlined.Home,
    iconTextId = R.string.main_nav_home,
    navTitleId = R.string.home_title
  ),
  RawrDestination(
    route = RawrRoute.FAVORITE,
    icon = Icons.Default.FavoriteBorder,
    iconTextId = R.string.main_nav_favorite,
    navTitleId = R.string.favorite_title
  ),
)

val ALL_ROUTES = TOP_LEVEL_DESTINATIONS + listOf(
  RawrDestination(
    route = RawrRoute.GAME_DETAIL,
    navTitleId = R.string.games_detail_title
  )
)