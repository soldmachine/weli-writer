package com.szoldapps.weli.writer.presentation.round.overview.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowButton
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowHeader
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem.RoundRowValues

internal class RoundValueRvAdapter(
    private val onItemClickListener: (Int) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var indexOfRound = -1
    private val list: MutableList<RoundValueRvAdapterItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ROUND_ROW_HEADER -> RoundHeaderViewHolder(inflater, parent)
            ROUND_ROW_VALUES -> RoundValueViewHolder(inflater, parent)
            ROUND_ROW_BUTTON -> RoundRowButtonViewHolder(inflater, parent)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.viewType) {
            ROUND_ROW_HEADER -> (holder as RoundHeaderViewHolder).bind(item as RoundRowHeader)
            ROUND_ROW_VALUES -> (holder as RoundValueViewHolder).bind(
                indexOfRound,
                item as RoundRowValues,
                onItemClickListener
            )
            ROUND_ROW_BUTTON -> (holder as RoundRowButtonViewHolder).bind(item as RoundRowButton)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].viewType

    override fun getItemCount(): Int = list.size

    fun refresh(indexOfRound: Int, list: List<RoundValueRvAdapterItem>) {
        this.indexOfRound = indexOfRound
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class RoundHeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_header, parent, false)) {

        private var roundHeader0 = itemView.findViewById<TextView>(R.id.roundHeader0)
        private var roundHeader1 = itemView.findViewById<TextView>(R.id.roundHeader1)
        private var roundHeader2 = itemView.findViewById<TextView>(R.id.roundHeader2)
        private var roundHeader3 = itemView.findViewById<TextView>(R.id.roundHeader3)

        fun bind(rowHeader: RoundRowHeader) {
            roundHeader0.text = rowHeader.titles[0]
            roundHeader1.text = rowHeader.titles[1]
            roundHeader2.text = rowHeader.titles[2]
            roundHeader3.text = rowHeader.titles[3]
        }
    }

    class RoundValueViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_row, parent, false)) {

        private var numberTv = itemView.findViewById<TextView>(R.id.numberTv)
        private var roundRow0 = itemView.findViewById<TextView>(R.id.roundRow0)
        private var roundRow1 = itemView.findViewById<TextView>(R.id.roundRow1)
        private var roundRow2 = itemView.findViewById<TextView>(R.id.roundRow2)
        private var roundRow3 = itemView.findViewById<TextView>(R.id.roundRow3)

        fun bind(
            indexOfRound: Int,
            rowValues: RoundRowValues,
            onItemClickListener: (Int) -> Unit
        ) {
            val number = rowValues.number
            numberTv.text = if (number == 0) "-" else number.toString()

            // roundRow0
            roundRow0.setTypeface(null, getTypeface(number, 1, indexOfRound))
            roundRow0.text = rowValues.values[0].toString()

            // roundRow1
            roundRow1.setTypeface(null, getTypeface(number, 2, indexOfRound))
            roundRow1.text = rowValues.values[1].toString()

            // roundRow2
            roundRow2.setTypeface(null, getTypeface(number, 3, indexOfRound))
            roundRow2.text = rowValues.values[2].toString()

            // roundRow3
            roundRow3.setTypeface(null, getTypeface(number, 0, indexOfRound))
            roundRow3.text = rowValues.values[3].toString()

            itemView.setOnClickListener { onItemClickListener.invoke(number) }
        }

        private fun getTypeface(number: Int, rest: Int, indexOfRound: Int) =
            if (number > 0 && ((number + indexOfRound) % 4) == rest) {
                Typeface.BOLD
            } else {
                Typeface.NORMAL
            }
    }

    class RoundRowButtonViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_round_button, parent, false)) {

        private var addRoundBtn = itemView.findViewById<Button>(R.id.itemRoundBtn)

        fun bind(roundRowButton: RoundRowButton) {
            addRoundBtn.text = roundRowButton.label
            addRoundBtn.setOnClickListener { roundRowButton.action.invoke() }
        }
    }

    companion object {
        const val ROUND_ROW_HEADER = 0
        const val ROUND_ROW_VALUES = 1
        const val ROUND_ROW_BUTTON = 2
    }
}
