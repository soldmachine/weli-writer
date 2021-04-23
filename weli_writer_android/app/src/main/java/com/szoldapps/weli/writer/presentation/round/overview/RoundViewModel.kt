package com.szoldapps.weli.writer.presentation.round.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.domain.RoundValueRvAdapterItem
import com.szoldapps.weli.writer.domain.WeliRepository
import com.szoldapps.weli.writer.presentation.common.helper.SingleLiveEvent
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewEvent.OpenBottomSheet
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Content
import com.szoldapps.weli.writer.presentation.round.overview.RoundViewState.Loading
import kotlinx.coroutines.launch

internal class RoundViewModel @ViewModelInject constructor(
    private val weliRepository: WeliRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData<RoundViewState>()
    val viewState: LiveData<RoundViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<RoundViewEvent>()
    val viewEvent: LiveData<RoundViewEvent> = _viewEvent

    fun loadContent(roundId: Long) = viewModelScope.launch {
        _viewState.postValue(Loading)
        val rvAdapterItems = weliRepository.roundValueRvAdapterItemsByRoundId(roundId) {
            _viewEvent.postValue(OpenBottomSheet)
        }
        _viewState.postValue(Content(rvAdapterItems))
    }
}

internal sealed class RoundViewState {
    object Loading : RoundViewState()
    object Error : RoundViewState()
    data class Content(val rounds: List<RoundValueRvAdapterItem>) : RoundViewState()
}

internal sealed class RoundViewEvent {
    object OpenBottomSheet : RoundViewEvent()
}
