package com.szoldapps.weliwriterkmp.data.extensions

import com.szoldapps.weliwriterkmp.domain.Player


fun List<Player>.mapToInitials() = this.map { player -> "${player.firstName.first()}${player.lastName.first()}" }
