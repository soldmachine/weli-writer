package com.szoldapps.weli.writer.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Game

class MatchRvAdapter(
    private val onItemClickListener: (Int) -> (Unit)
) : RecyclerView.Adapter<MatchRvAdapter.MatchViewHolder>() {

    private val list: MutableList<Game> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MatchViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<Game>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class MatchViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(game: Game, onItemClickListener: (Int) -> (Unit)) {
            titleTv.text = "${game.date}"
            titleTv.setOnClickListener {
                onItemClickListener.invoke(game.id)
            }
        }

    }
}
