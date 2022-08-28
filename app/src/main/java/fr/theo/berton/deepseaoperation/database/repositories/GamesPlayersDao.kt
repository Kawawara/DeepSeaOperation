package fr.theo.berton.deepseaoperation.database.repositories

import androidx.room.*
import fr.theo.berton.deepseaoperation.model.GamesPlayers

@Dao
interface GamesPlayersDao {
    @Insert
    fun insertOne(game: GamesPlayers): Long
}