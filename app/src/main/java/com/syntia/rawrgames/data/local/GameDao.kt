package com.syntia.rawrgames.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.syntia.rawrgames.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query(value = "SELECT * FROM games")
    fun getGames(): Flow<List<GameEntity>>

    @Query(value = "SELECT * FROM games WHERE id == :id")
    fun getGameById(id: String): Flow<GameEntity?>

    @Upsert
    suspend fun upsertGame(game: GameEntity)

    @Query(value = "DELETE FROM games WHERE id == :id")
    suspend fun deleteGame(id: String)
}