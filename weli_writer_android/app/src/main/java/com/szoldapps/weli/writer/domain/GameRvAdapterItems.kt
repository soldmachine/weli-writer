package com.szoldapps.weli.writer.domain


sealed class GameRvAdapterItem(
    val viewType: Int
) {

    data class GameRowHeader(
        val titles: List<String>
    ) : GameRvAdapterItem(GAME_ROW_HEADER)

    data class GameRowValues(
        val id: Long,
        val number: Int,
        val values: List<Int>
    ) : GameRvAdapterItem(GAME_ROW_VALUES)

    data class GameRowButton(
        val label: String,
        val action: () -> (Unit)
    ) : GameRvAdapterItem(GAME_ROW_BUTTON)

}

const val GAME_ROW_HEADER = 0
const val GAME_ROW_VALUES = 1
const val GAME_ROW_BUTTON = 2
