package com.szoldapps.weli.writer.presentation.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.Round
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class GameRvAdapter(
    private val onItemClickListener: (Long) -> (Unit)
) : RecyclerView.Adapter<GameRvAdapter.RoundViewHolder>() {

    private val list: MutableList<Round> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RoundViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RoundViewHolder, position: Int) {
        holder.bind(position, list[position], onItemClickListener)
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

        fun bind(position: Int, round: Round, onItemClickListener: (Long) -> Unit) {
            titleTv.text = "Round ${position + 1} (${round.date.formatted()})"
            titleTv.setOnClickListener {
                onItemClickListener.invoke(round.id)
            }
        }

        private fun OffsetDateTime.formatted(): String =
            this.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    companion object {
        const val GAME_ROW_HEADER = 0
        const val GAME_ROW_VALUES = 1
        const val GAME_ROW_BUTTON = 2
    }
}
