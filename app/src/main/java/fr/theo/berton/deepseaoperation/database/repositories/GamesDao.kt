package fr.theo.berton.deepseaoperation.database.repositories

import androidx.room.*
import fr.theo.berton.deepseaoperation.model.Game
import fr.theo.berton.deepseaoperation.model.GameWithPlayers

@Dao
interface GamesDao {
    @Insert
    fun insertOne(game: Game): Long

    @Update
    fun updateOne(game:Game)

    @Transaction
    @Query("SELECT * FROM games WHERE game_id= :gameId")
    fun getGameWithPlayer(gameId:Long): GameWithPlayers

    @Transaction
    @Query("SELECT * FROM games")
    fun getAllGamesWithPlayers(): List<GameWithPlayers>
}