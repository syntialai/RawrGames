package com.syntia.rawrgames.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.syntia.rawrgames.R
import com.syntia.rawrgames.ui.model.Game
import com.syntia.rawrgames.ui.theme.RawrGamesTheme
import com.syntia.rawrgames.ui.theme.Yellow
import com.syntia.rawrgames.ui.uiState.GamesUiState

@Composable
fun GameCards(
  gamesUiState: GamesUiState,
  showSnackbar: (String?) -> Unit,
  onClick: (id: String) -> Unit,
  modifier: Modifier = Modifier,
  verticalSpacer: Boolean = false
) {
  when(gamesUiState) {
    is GamesUiState.Loading -> {
      LoadingSection()
    }
    is GamesUiState.Success -> {
      GameCards(
        games = gamesUiState.games,
        onClick = onClick,
        modifier = modifier,
        verticalSpacer = verticalSpacer
      )
    }
    is GamesUiState.Error -> {
      showSnackbar(gamesUiState.message)
    }
  }
}

@Composable
fun GameCards(
  games: List<Game>,
  onClick: (id: String) -> Unit,
  modifier: Modifier = Modifier,
  verticalSpacer: Boolean = false
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    gamesVerticalSpacer(verticalSpacer = verticalSpacer, suffixKey = "leading")

    itemsIndexed(games, key = { _, item -> item.id }) { _, game ->
      GameCard(game = game, onClick = onClick)
    }

    gamesVerticalSpacer(verticalSpacer = verticalSpacer, suffixKey = "trailing")
  }
}

private fun LazyListScope.gamesVerticalSpacer(
  verticalSpacer: Boolean,
  suffixKey: String
) {
  if (verticalSpacer) {
    item(key = "games_spacer_$suffixKey") {
      Spacer(modifier = Modifier.height(1.dp))
    }
  }
}

@Composable
fun GameCard(
  game: Game,
  onClick: (id: String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  ElevatedCard (
    onClick = { onClick(game.id) },
    modifier = modifier
  ) {
    AsyncImage(
      model = ImageRequest.Builder(context)
        .data(game.imageUrl)
        .build(),
      contentDescription = game.name,
      contentScale = ContentScale.Crop,
      modifier = Modifier.aspectRatio(4f / 3f)
    )

    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = game.name,
        style = MaterialTheme.typography.titleMedium
      )

      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = game.rating.toString(),
          style = MaterialTheme.typography.labelMedium
        )
        Image(
          imageVector = Icons.Filled.Star,
          contentDescription = "rating_star",
          colorFilter = ColorFilter.tint(Yellow),
          modifier = Modifier.size(16.dp)
        )

        Text(
          text = stringResource(id = R.string.icon_circle),
          style = MaterialTheme.typography.labelMedium,
          modifier = Modifier.padding(horizontal = 4.dp)
        )

        Text(
          text = game.releaseDate,
          style = MaterialTheme.typography.labelMedium,
          modifier = Modifier.padding(start = 2.dp)
        )
      }
    }
  }
}

@Composable
@Preview(showBackground = true, device = Devices.NEXUS_6)
private fun GameCardPreview() {
  val game = Game(
    id = "1213321321",
    name = "Vampire: The Masquerade - Bloodlines 2",
    releaseDate = "Sep 2022",
    imageUrl = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
    rating = 4.5f
  )

  RawrGamesTheme {
    GameCard(
      game = game,
      onClick = {}
    )
  }
}