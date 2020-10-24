package com.szoldapps.weli.writer.presentation.match

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.Match
import com.szoldapps.weli.writer.domain.WeliRepository
import kotlinx.coroutines.launch
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

class MatchViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository
) : ViewModel() {

    val result: MutableLiveData<String> = MutableLiveData("")

    fun getMatches() {
        viewModelScope.launch {
            weliRepository.addMatch(Match(OffsetDateTime.now(), "testLocation"))
            Timber.i(weliRepository.getMatches().toString())
        }
    }
}
