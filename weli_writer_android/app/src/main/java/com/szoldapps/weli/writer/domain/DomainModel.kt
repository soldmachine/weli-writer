package com.szoldapps.weli.writer.domain

import com.szoldapps.weli.writer.presentation.round.adapter.RoundValueRvAdapter.Companion.ROUND_ROW_HEADER
import com.szoldapps.weli.writer.presentation.round.adapter.RoundValueRvAdapter.Companion.ROUND_ROW_VALUES
import com.szoldapps.weli.writer.presentation.round.adapter.RoundValueRvAdapter.Companion.ROUND_VALUE
import org.threeten.bp.OffsetDateTime

data class Match(
    val id: Long = -1,
    val date: OffsetDateTime,
    val location: String
)

data class Player(
    val id: Long = -1,
    val firstName: String,
    val lastName: String
)

data class Game(
    val id: Long = -1,
    val date: OffsetDateTime,
    val players: List<Player> = emptyList()
)

data class Round(
    val id: Long = -1,
    val date: OffsetDateTime,
)

sealed class RoundRvAdapterValue(
    val viewType: Int
) {

    data class RoundRowHeader(
        val titles: List<String>
    ) : RoundRvAdapterValue(ROUND_ROW_HEADER)

    data class RoundRowValues(
        val number: Int,
        val values: List<Int>
    ) : RoundRvAdapterValue(ROUND_ROW_VALUES)

    data class RoundValue(
        val id: Long = -1,
        val date: OffsetDateTime,
        val number: Int,
        val value: Int
    ) : RoundRvAdapterValue(ROUND_VALUE)
}
