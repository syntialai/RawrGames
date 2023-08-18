package com.syntia.rawrgames.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(

  @PrimaryKey
  @ColumnInfo(name = "id")
  val id: String,

  @ColumnInfo(name = "name")
  val name: String,

  @ColumnInfo(name = "releaseDate")
  val releaseDate: String,

  @ColumnInfo(name = "imageUrl")
  val imageUrl: String,

  @ColumnInfo(name = "rating")
  val rating: Float
)
