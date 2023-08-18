package com.syntia.rawrgames.data.network.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
  val id: String? = null,
  val name: String? = null,
  @SerializedName("background_image")
  val backgroundImage: String? = null,
  val rating: Float? = null,
  @SerializedName("rating_top")
  val ratingTop: Float? = null,
  @SerializedName("ratings_count")
  val ratingsCount: Float? = null,
  val released: String? = null,
  val description: String? = null,
  val playtime: Int? = null
)