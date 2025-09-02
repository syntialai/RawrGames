package com.syntia.rawrgames.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.syntia.rawrgames.R
import com.syntia.rawrgames.ui.component.LoadingSection
import com.syntia.rawrgames.ui.model.GameDetail
import com.syntia.rawrgames.ui.theme.RawrGamesTheme
import com.syntia.rawrgames.ui.theme.Yellow
import com.syntia.rawrgames.ui.uiState.GameDetailUiState

@Composable
fun GameDetailScreen(
  viewModel: GameDetailViewModel = hiltViewModel(),
  showSnackbar: (String?) -> Unit
) {
  val gameDetailUiState by viewModel.gameDetailUiState.collectAsStateWithLifecycle()
  val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

  GameDetailScreen(
    gameDetailUiState = gameDetailUiState,
    isFavorite = isFavorite,
    showSnackbar = showSnackbar,
    updateFavorite = { value ->
      val gameDetail = (gameDetailUiState as? GameDetailUiState.Success)?.gameDetail
      gameDetail?.let {
        viewModel.updateFavorite(value, gameDetail)
      }
    }
  )
}

@Composable
private fun GameDetailScreen(
  gameDetailUiState: GameDetailUiState,
  isFavorite: Boolean,
  showSnackbar: (String?) -> Unit,
  updateFavorite: (Boolean) -> Unit
) {
  val scrollState = rememberScrollState()

  Column(modifier = Modifier
    .fillMaxSize()
    .verticalScroll(scrollState)) {
    when (gameDetailUiState) {
      is GameDetailUiState.Loading -> {
        LoadingSection()
      }

      is GameDetailUiState.Success -> {
        GameDetailScreenContent(
          game = gameDetailUiState.gameDetail,
          isFavorite = isFavorite,
          updateFavorite = updateFavorite
        )
      }

      is GameDetailUiState.Error -> {
        showSnackbar(gameDetailUiState.message)
      }
    }
  }
}

@Composable
private fun GameDetailScreenContent(
  game: GameDetail,
  isFavorite: Boolean,
  updateFavorite: (Boolean) -> Unit
) {
  val context = LocalContext.current

  Box {
    AsyncImage(
      model = ImageRequest.Builder(context)
        .data(game.imageUrl)
        .build(),
      contentDescription = game.name,
      contentScale = ContentScale.Crop,
      modifier = Modifier.aspectRatio(4f / 3f)
    )

    IconToggleButton(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      checked = isFavorite,
      onCheckedChange = updateFavorite,
      colors = IconButtonDefaults.iconToggleButtonColors(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .5f)
      )
    ) {
      Icon(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "favorite",
        tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
      )
    }
  }

  Spacer(modifier = Modifier.height(16.dp))

  if (game.publisher.isNotBlank()) {
    Text(
      text = game.publisher,
      style = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray
      ),
      modifier = Modifier.horizontalPaddingModifier()
    )
  }

  Text(
    text = game.name,
    style = MaterialTheme.typography.titleLarge,
    modifier = Modifier
      .horizontalPaddingModifier()
      .padding(top = 4.dp)
  )

  Text(
    text = if (game.releaseDate.isNotBlank()) {
      stringResource(id = R.string.release_date_label, game.releaseDate)
    } else {
      stringResource(id = R.string.to_be_released)
    },
    style = MaterialTheme.typography.labelMedium.copy(
      color = Color.Gray
    ),
    modifier = Modifier
      .horizontalPaddingModifier()
      .padding(top = 4.dp)
  )

  Row(
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .horizontalPaddingModifier()
      .padding(top = 4.dp)
  ) {
    Image(
      imageVector = Icons.Filled.Star,
      contentDescription = "rating_star",
      colorFilter = ColorFilter.tint(Yellow),
      modifier = Modifier.size(16.dp)
    )

    Text(
      text = game.rating.toString(),
      style = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray
      )
    )

    Image(
      imageVector = Icons.Outlined.CheckCircle,
      contentDescription = "played_count",
      colorFilter = ColorFilter.tint(Color.DarkGray),
      modifier = Modifier.size(16.dp)
    )

    Text(
      text = game.playedCount.toString(),
      style = MaterialTheme.typography.labelMedium.copy(
        color = Color.Gray
      )
    )
  }

  Text(
    text = game.description,
    style = MaterialTheme.typography.bodyMedium,
    modifier = Modifier
      .horizontalPaddingModifier()
      .padding(top = 32.dp)
  )
}

private fun Modifier.horizontalPaddingModifier() = padding(horizontal = 16.dp)

@Composable
@Preview(showBackground = true, device = Devices.NEXUS_6)
fun GameDetailScreenPreview() {
  val gameDetail = GameDetail(
    id = "1213321321",
    name = "Vampire: The Masquerade - Bloodlines 2",
    releaseDate = "2021-08-20",
    imageUrl = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
    rating = 4.5f,
    description = "Ini description",
    playedCount = 500L,
    publisher = "Publisher"
  )

  RawrGamesTheme {
    GameDetailScreen(
      gameDetailUiState = GameDetailUiState.Success(gameDetail),
      isFavorite = true,
      showSnackbar = { true },
      updateFavorite = {}
    )
  }
}