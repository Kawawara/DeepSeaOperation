package fr.theo.berton.deepseaoperation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.theo.berton.deepseaoperation.model.Player

class WaitingRoomAdapter(
    val context: Context,
    var players: List<Player>,

    ): RecyclerView.Adapter<WaitingRoomAdapter.ViewHolder> (){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val playerName = view.findViewById<TextView>(R.id.waiting_player_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.player_waiting_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPlayer = players[position]
        holder.playerName.text = currentPlayer.name
    }

    override fun getItemCount(): Int = players.size
}