package fr.theo.berton.deepseaoperation.database

import androidx.room.Database
import androidx.room.RoomDatabase

import fr.theo.berton.deepseaoperation.model.Game
import fr.theo.berton.deepseaoperation.model.GamesPlayers
import fr.theo.berton.deepseaoperation.model.Player

import fr.theo.berton.deepseaoperation.database.repositories.GamesDao
import fr.theo.berton.deepseaoperation.database.repositories.GamesPlayersDao
import fr.theo.berton.deepseaoperation.database.repositories.PlayerDao

@Database(
    entities = [Player::class, Game::class, GamesPlayers::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun playersDao(): PlayerDao
    abstract fun gamesDao(): GamesDao
    abstract fun gamesPlayersDao(): GamesPlayersDao
}