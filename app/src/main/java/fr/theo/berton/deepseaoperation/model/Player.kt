package fr.theo.berton.deepseaoperation.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id")
    val id:Long=0,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "ip_address")
    var ipAddress:String,
    val port:Int,
){
    @Ignore
    var status:Boolean = false
}
