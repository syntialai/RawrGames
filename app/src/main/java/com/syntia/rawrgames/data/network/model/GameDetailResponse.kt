package com.syntia.rawrgames.data.network.model

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
  val added: Long? = null,
  @SerializedName("background_image")
  val backgroundImage: String? = null,
  @SerializedName("description_raw")
  val descriptionRaw: String? = null,
  val id: String? = null,
  val name: String? = null,
  val rating: Float? = null,
  val publishers: List<Publisher>? = null,
  val released: String? = null,
)