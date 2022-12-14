package fr.theo.berton.deepseaoperation.model

import androidx.room.*

@Entity(tableName = "games")
data class Game(
    val date:Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val id : Long,
    var status:String,
    @ColumnInfo(name = "ip_address")
    val ipAddress:String,
    val role:String,
    var turn:Int=1,
    var shipIntegrity:Int=100
)

@Entity(tableName = "games_players",primaryKeys = ["game_id", "player_id"] )
data class GamesPlayers(
    @ColumnInfo(name = "game_id") val gameId: Long,
    @ColumnInfo(name = "player_id") val playerId: Long
)

data class GameWithPlayers(
    @Embedded var game: Game,
    @Relation(
        parentColumn = "game_id",
        entityColumn = "player_id",
        associateBy = Junction(GamesPlayers::class)
    )
    val players:List<Player>
)

enum class GameStatusType {
    started,
    finished,
    pending,
    unfinished
}
enum class RoleType {
    server,
    client
}
