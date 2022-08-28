package fr.theo.berton.deepseaoperation.database.repositories

import androidx.room.*
import fr.theo.berton.deepseaoperation.model.Player

@Dao
interface PlayerDao {
    @Insert
    fun insertOne(player: Player): Long
    @Update
    fun updateOne(player: Player) : Int

    @Query("SELECT COUNT(*) FROM players WHERE name = :name")
    fun doesUserExist(name: String): Int

    @Query("""
    SELECT * 
    FROM 
        players u
    WHERE
        u.name = :name
    LIMIT 1
  """)
    fun getByUsername(name: String): Player?

}