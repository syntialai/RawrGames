package com.syntia.rawrgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.syntia.rawrgames.ui.screen.main.MainScreen
import com.syntia.rawrgames.ui.theme.RawrGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()
    setContent {
      RawrGamesTheme {
        MainScreen()
      }
    }
  }
}