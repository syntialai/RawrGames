package com.syntia.rawrgames.ui.screen.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.syntia.rawrgames.ui.navigation.RawrDestination
import com.syntia.rawrgames.ui.navigation.RawrNavHost
import com.syntia.rawrgames.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.syntia.rawrgames.ui.navigation.navigateToSingleTop
import com.syntia.rawrgames.ui.theme.RawrGamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
  val appState: MainAppState = rememberMainAppState(LocalContext.current)

  BackHandler {
    appState.onBackPressed()
  }

  Scaffold(
    topBar = {
      MainTopAppBar(
        currentPageTitle = appState.currentPageTitle,
        currentTopLevelDestination = appState.currentTopLevelDestination,
        popBackStack = appState::onBackPressed
      )
    },
    bottomBar = {
      MainBottomNavigation(
        currentTopLevelDestination = appState.currentTopLevelDestination,
        navigateToTopLevelDestination = { rawrDestination: RawrDestination ->
          appState.navController.navigateToSingleTop(rawrDestination.route)
        }
      )
    },
    snackbarHost = { SnackbarHost(appState.snackbarHostState) }
  ) { contentPadding ->
    RawrNavHost(
      appState = appState,
      modifier = Modifier.padding(contentPadding)
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopAppBar(
  currentPageTitle: String,
  currentTopLevelDestination: RawrDestination?,
  popBackStack: () -> Unit
) {
  TopAppBar(
    title = {
      Text(text = currentPageTitle)
    },
    navigationIcon = {
      if (currentTopLevelDestination == null) {
        IconButton(onClick = popBackStack) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
            contentDescription = null
          )
        }
      }
    },
    actions = {
      // No Implementation Required
    }
  )
}

@Composable
private fun MainBottomNavigation(
  currentTopLevelDestination: RawrDestination?,
  navigateToTopLevelDestination: (RawrDestination) -> Unit
) {
  if (TOP_LEVEL_DESTINATIONS.contains(currentTopLevelDestination)) {
    NavigationBar {
      TOP_LEVEL_DESTINATIONS.forEach { mainDestination ->
        NavigationBarItem(
          selected = currentTopLevelDestination?.route == mainDestination.route,
          onClick = { navigateToTopLevelDestination(mainDestination) },
          icon = {
            if (listOfNotNull(mainDestination.icon, mainDestination.iconTextId).size == 2) {
              Icon(
                imageVector = mainDestination.icon!!,
                contentDescription = stringResource(id = mainDestination.iconTextId!!)
              )
            }
          },
          label = {
            if (mainDestination.iconTextId != null) {
              Text(text = stringResource(id = mainDestination.iconTextId))
            }
          }
        )
      }
    }
  }
}

@Composable
@Preview(showBackground = true, device = Devices.NEXUS_6)
private fun MainScreenPreview() {
  RawrGamesTheme {
    MainScreen()
  }
}