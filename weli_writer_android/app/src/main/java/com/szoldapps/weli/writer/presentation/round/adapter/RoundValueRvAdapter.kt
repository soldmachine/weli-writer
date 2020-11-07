package com.szoldapps.weli.writer.presentation.round.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.domain.RoundValue

class RoundValueRvAdapter : RecyclerView.Adapter<RoundValueRvAdapter.RoundValueViewHolder>() {

    private val list: MutableList<RoundValue> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundValueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RoundValueViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RoundValueViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun refresh(list: List<RoundValue>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class RoundValueViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_match, parent, false)) {

        private var titleTv = itemView.findViewById<TextView>(R.id.matchTitleTv)

        fun bind(round: RoundValue) {
            titleTv.text = "${round.number}: ${round.value}"
        }

    }
}
