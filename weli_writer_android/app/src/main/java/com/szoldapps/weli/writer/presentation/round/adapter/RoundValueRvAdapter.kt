package com.szoldapps.weli.writer.presentation.round.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundHeader
import com.szoldapps.weli.writer.domain.RoundRvAdapterValue.RoundValue

internal class RoundValueRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<RoundRvAdapterValue> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ROUND_HEADER -> RoundHeaderViewHolder(inflater, parent)
            ROUND_VALUE -> RoundValueViewHolder(inflater, parent)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.viewType) {
            ROUND_HEADER -> (holder as RoundHeaderViewHolder).bind(item as RoundHeader)
            ROUND_VALUE -> (holder as RoundValueViewHolder).bind(item as RoundValue)
            else -> throw IllegalStateException("Unknown viewType!")
        }
    }

    override fun getItemViewType(position: Int): Int = list[position].viewType

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<RoundRvAdapterValue>) {
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

        fun bind(header: RoundHeader) {
            roundHeader0.text = header.titles[0]
            roundHeader1.text = header.titles[1]
            roundHeader2.text = header.titles[2]
            roundHeader3.text = header.titles[3]
        }
    }


    class RoundValueViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)
        fun bind(round: RoundValue) {
            titleTv.text = "${round.number}: ${round.value}"
        }
    }

    companion object {
        const val ROUND_HEADER = 0
        const val ROUND_VALUE = 1
    }
}
