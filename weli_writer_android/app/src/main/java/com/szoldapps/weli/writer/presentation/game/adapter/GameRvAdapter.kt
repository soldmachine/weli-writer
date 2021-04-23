package com.szoldapps.weli.writer.presentation.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.GAME_ROW_BUTTON
import com.szoldapps.weli.writer.domain.GAME_ROW_HEADER
import com.szoldapps.weli.writer.domain.GAME_ROW_VALUES
import com.szoldapps.weli.writer.domain.GameRvAdapterItem
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowButton
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowHeader
import com.szoldapps.weli.writer.domain.GameRvAdapterItem.GameRowValues
import com.szoldapps.weli.writer.domain.Round
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class GameRvAdapter(
    private val onItemClickListener: (Long) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<GameRvAdapterItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            GAME_ROW_HEADER -> GameHeaderViewHolder(inflater, parent)
            GAME_ROW_VALUES -> GameValueViewHolder(inflater, parent)
            GAME_ROW_BUTTON -> GameRowButtonViewHolder(inflater, parent)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.viewType) {
            GAME_ROW_HEADER -> (holder as GameHeaderViewHolder).bind(item as GameRowHeader)
            GAME_ROW_VALUES -> (holder as GameValueViewHolder).bind(item as GameRowValues, onItemClickListener)
            GAME_ROW_BUTTON -> (holder as GameRowButtonViewHolder).bind(item as GameRowButton)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].viewType

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<GameRvAdapterItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class GameHeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_header, parent, false)) {

        private var roundHeader0 = itemView.findViewById<TextView>(R.id.roundHeader0)
        private var roundHeader1 = itemView.findViewById<TextView>(R.id.roundHeader1)
        private var roundHeader2 = itemView.findViewById<TextView>(R.id.roundHeader2)
        private var roundHeader3 = itemView.findViewById<TextView>(R.id.roundHeader3)

        fun bind(rowHeader: GameRowHeader) {
            roundHeader0.text = rowHeader.titles[0]
            roundHeader1.text = rowHeader.titles[1]
            roundHeader2.text = rowHeader.titles[2]
            roundHeader3.text = rowHeader.titles[3]
        }
    }

    class GameValueViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_row, parent, false)) {

        private var numberTv = itemView.findViewById<TextView>(R.id.numberTv)
        private var roundRow0 = itemView.findViewById<TextView>(R.id.roundRow0)
        private var roundRow1 = itemView.findViewById<TextView>(R.id.roundRow1)
        private var roundRow2 = itemView.findViewById<TextView>(R.id.roundRow2)
        private var roundRow3 = itemView.findViewById<TextView>(R.id.roundRow3)

        fun bind(rowValues: GameRowValues, onItemClickListener: (Long) -> (Unit)) {
            numberTv.text = rowValues.number.plus(1).toString()
            roundRow0.text = rowValues.values[0].toString()
            roundRow1.text = rowValues.values[1].toString()
            roundRow2.text = rowValues.values[2].toString()
            roundRow3.text = rowValues.values[3].toString()
            itemView.setOnClickListener {
                onItemClickListener.invoke(rowValues.id)
            }
        }
    }

    class GameRowButtonViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_button, parent, false)) {

        private var addRoundBtn = itemView.findViewById<Button>(R.id.itemRoundBtn)

        fun bind(roundRowButton: GameRowButton) {
            addRoundBtn.text = roundRowButton.label
            addRoundBtn.setOnClickListener { roundRowButton.action.invoke() }
        }
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
}
