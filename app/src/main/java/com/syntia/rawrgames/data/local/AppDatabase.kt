package com.syntia.rawrgames.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syntia.rawrgames.data.local.AppDatabase.Companion.DB_VERSION
import com.syntia.rawrgames.data.local.entity.GameEntity

@Database(
  version = DB_VERSION,
  exportSchema = false,
  entities = [GameEntity::class],
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun gameDao(): GameDao

  companion object {
    const val DB_VERSION = 1
    const val DB_NAME = "rawrDB"

    @JvmStatic
    fun buildDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }
  }
}