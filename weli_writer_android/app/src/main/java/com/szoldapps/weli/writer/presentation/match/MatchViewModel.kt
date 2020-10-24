package com.szoldapps.weli.writer.presentation.match

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
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

    private val _viewState: MutableLiveData<MatchViewState> = MutableLiveData(MatchViewState.Loading)
    val viewState: LiveData<MatchViewState> = _viewState

    fun getMatches() {
        viewModelScope.launch {
            _viewState.postValue(MatchViewState.Content(weliRepository.getMatches()))
        }
    }

    fun addRandomMatch() {
        viewModelScope.launch {
            weliRepository.addMatch(Match(OffsetDateTime.now(), "testLocation"))
            getMatches()
        }
    }

}

sealed class MatchViewState {
    object Loading : MatchViewState()
    object Error : MatchViewState()
    data class Content(val matches: List<Match>) : MatchViewState()
}
