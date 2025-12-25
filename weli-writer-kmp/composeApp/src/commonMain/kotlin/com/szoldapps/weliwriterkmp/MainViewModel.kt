package com.szoldapps.weliwriterkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepositoryImpl,
) : ViewModel() {

    internal val uiState: StateFlow<UiState>
        field = MutableStateFlow<UiState>(UiState.Loading)

    init {
        loadContent()
    }

    fun loadContent() {
        viewModelScope.launch {
            uiState.value = UiState.Content(
                buttonLabel = "Click Me",
                text = repository.getGithubRepoEntities().toString(),
                clickAction = {
                    insertAndLoadContent()
                }
            )
        }
    }

    private fun insertAndLoadContent() {
        viewModelScope.launch {
            repository.insertGithubRepoEntity()
            loadContent()
        }
    }

    internal sealed class UiState {
        data object Loading : UiState()
        data class Content(
            val buttonLabel: String,
            val text: String,
            val clickAction: () -> Unit,
        ) : UiState()

        data object Error : UiState()
    }
}
