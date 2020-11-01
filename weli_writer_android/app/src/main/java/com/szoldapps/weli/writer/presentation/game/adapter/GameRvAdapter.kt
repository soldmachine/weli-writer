package com.szoldapps.weli.writer.presentation.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Match

class GameRvAdapter : RecyclerView.Adapter<GameRvAdapter.GameViewHolder>() {

    private val list: MutableList<Match> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<Match>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(match: Match) {
            titleTv.text = "${match.date}, ${match.location}"
        }

    }
}
