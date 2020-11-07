package com.szoldapps.weli.writer.presentation.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Round

class RoundRvAdapter(
    private val onItemClickListener: (Long) -> (Unit)
) : RecyclerView.Adapter<RoundRvAdapter.RoundViewHolder>() {

    private val list: MutableList<Round> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RoundViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RoundViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<Round>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class RoundViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(round: Round, onItemClickListener: (Long) -> Unit) {
            titleTv.text = "${round.date}"
            titleTv.setOnClickListener {
                onItemClickListener.invoke(round.id)
            }
        }

    }
}
