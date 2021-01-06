package com.szoldapps.weli.writer.domain

import com.szoldapps.weli.writer.presentation.round.overview.adapter.RoundValueRvAdapter.Companion.ROUND_ROW_BUTTON
import com.szoldapps.weli.writer.presentation.round.overview.adapter.RoundValueRvAdapter.Companion.ROUND_ROW_HEADER
import com.szoldapps.weli.writer.presentation.round.overview.adapter.RoundValueRvAdapter.Companion.ROUND_ROW_VALUES
import com.szoldapps.weli.writer.presentation.round.overview.adapter.RoundValueRvAdapter.Companion.ROUND_VALUE
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

sealed class RoundValueRvAdapterItem(
    val viewType: Int
) {

    data class RoundRowHeader(
        val titles: List<String>
    ) : RoundValueRvAdapterItem(ROUND_ROW_HEADER)

    data class RoundRowValues(
        val number: Int,
        val values: List<Int>
    ) : RoundValueRvAdapterItem(ROUND_ROW_VALUES)

    data class RoundRowButton(
        val label: String,
        val action: () -> (Unit)
    ) : RoundValueRvAdapterItem(ROUND_ROW_BUTTON)

    data class RoundValue(
        val id: Long = -1,
        val date: OffsetDateTime,
        val number: Int,
        val value: Int
    ) : RoundValueRvAdapterItem(ROUND_VALUE)
}
