package com.syntia.rawrgames.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.syntia.rawrgames.R
import com.syntia.rawrgames.ui.model.Game
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

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick(game.id) }
    ,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    AsyncImage(
      model = ImageRequest.Builder(context)
        .data(game.imageUrl)
        .build(),
      contentDescription = game.name,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .aspectRatio(4f / 3f)
        .weight(.35f)
        .clip(RoundedCornerShape(4.dp))
    )

    Column(
      modifier = Modifier.weight(.65f),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = game.name,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 4.dp)
      )

      Text(
        text = stringResource(id = R.string.release_date_label, game.releaseDate),
        style = MaterialTheme.typography.labelMedium
      )

      Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          imageVector = Icons.Filled.Star,
          contentDescription = "rating_star",
          colorFilter = ColorFilter.tint(Yellow),
          modifier = Modifier.size(16.dp)
        )

        Text(
          text = game.rating.toString(),
          style = MaterialTheme.typography.labelMedium
        )
      }
    }
  }
}