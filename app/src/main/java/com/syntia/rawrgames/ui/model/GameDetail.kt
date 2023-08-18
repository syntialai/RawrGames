package com.syntia.rawrgames.ui.model

data class GameDetail(

  val id: String,

  val name: String,

  val releaseDate: String,

  val imageUrl: String,

  val rating: Float,

  val playedCount: Long,

  val description: String,

  val publisher: String
)
