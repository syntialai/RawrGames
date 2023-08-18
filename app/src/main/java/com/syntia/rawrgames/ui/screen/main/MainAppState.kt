package com.syntia.rawrgames.ui.screen.main

import android.app.Activity
import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.syntia.rawrgames.ui.navigation.ALL_ROUTES
import com.syntia.rawrgames.ui.navigation.RawrDestination
import com.syntia.rawrgames.ui.navigation.TOP_LEVEL_DESTINATIONS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberMainAppState(
    context: Context,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(context, navController, snackbarHostState, coroutineScope) {
  MainAppState(
    context = context,
    navController = navController,
    snackbarHostState = snackbarHostState,
    coroutineScope = coroutineScope
  )
}

@Stable
class MainAppState(
    private val context: Context,
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) {

  private val currentBackStackEntry: NavBackStackEntry?
    @Composable get() = navController.currentBackStackEntryAsState().value

  val currentTopLevelDestination: RawrDestination?
    @Composable get() = TOP_LEVEL_DESTINATIONS.firstOrNull {
      it.route == currentBackStackEntry?.destination?.route
    }

  val currentPageTitle: String
    @Composable get() = ALL_ROUTES.firstOrNull {
      it.route == currentBackStackEntry?.destination?.route
    }?.navTitleId?.let {
      stringResource(id = it)
    } ?: ""

  fun showSnackbar(message: String?) {
    val safeMessage = message.orEmpty()

    if (safeMessage.isNotBlank()) {
      coroutineScope.launch {
        snackbarHostState.showSnackbar(
          message = safeMessage,
          actionLabel = null,
          duration = SnackbarDuration.Short
        )
      }
    }
  }

  fun onBackPressed() {
    if (navController.popBackStack().not()) {
      (context as? Activity)?.finish()
    }
  }
}