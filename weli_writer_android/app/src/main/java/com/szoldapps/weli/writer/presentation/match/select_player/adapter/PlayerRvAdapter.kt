package com.szoldapps.weli.writer.presentation.match.select_player.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Player

class PlayerRvAdapter(
    private val onItemClickListener: (Player) -> (Unit)
) : RecyclerView.Adapter<PlayerRvAdapter.PlayerViewHolder>() {

    private val list: MutableList<Player> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlayerViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<Player>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class PlayerViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(player: Player, onItemClickListener: (Player) -> (Unit)) {
            titleTv.text = "${player.firstName} ${player.lastName}"
            titleTv.setOnClickListener {
                onItemClickListener.invoke(player)
            }
        }

    }
}
