package com.syntia.rawrgames.ui.navigation

object RawrRoute {
  const val HOME = "home"
  const val FAVORITE = "favorite"
  const val GAME_DETAIL = "game/{id}"

  fun constructRouteWithParam(route: String, vararg params: Pair<String, String>): String {
    var currentRoute = route
    params.forEach { param ->
      val (key, value) = param
      currentRoute = currentRoute.replace("{$key}", value)
    }
    return currentRoute
  }
}

object RawrArgs {
  const val NAV_TITLE = "navTitle"
  const val ID = "id"
}