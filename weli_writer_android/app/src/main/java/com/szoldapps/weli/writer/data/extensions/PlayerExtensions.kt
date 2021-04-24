package com.szoldapps.weli.writer.data.extensions

import com.szoldapps.weli.writer.domain.Player


fun List<Player>.mapToInitials() = this.map { player -> "${player.firstName.first()}${player.lastName.first()}" }
