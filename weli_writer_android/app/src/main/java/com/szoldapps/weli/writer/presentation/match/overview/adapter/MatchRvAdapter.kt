package com.szoldapps.weli.writer.presentation.match.overview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Game
import com.szoldapps.weli.writer.domain.Player

class MatchRvAdapter(
    private val onItemClickListener: (Long) -> (Unit)
) : ListAdapter<Game, MatchRvAdapter.MatchViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(game: Game, onItemClickListener: (Long) -> (Unit)) {
            titleTv.text = "Game ${game.id} (${game.players.getInitials()})"
            titleTv.setOnClickListener {
                onItemClickListener.invoke(game.id)
            }
        }

        private fun List<Player>.getInitials(): String =
            this.joinToString(separator = " | ") { player ->
                player.firstName.first().uppercaseChar().toString() + player.lastName.first().uppercaseChar().toString()
            }
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}
