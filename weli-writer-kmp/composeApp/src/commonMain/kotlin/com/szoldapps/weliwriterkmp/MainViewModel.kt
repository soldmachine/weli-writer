package com.szoldapps.weliwriterkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoDao
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: SomeRepoImpl,
    private val githubRepoDao: GithubRepoDao,
) : ViewModel() {

    internal val uiState: StateFlow<UiState>
        field = MutableStateFlow<UiState>(UiState.Loading)

    init {
        loadContent()
    }

    fun loadContent() {
        viewModelScope.launch {
            githubRepoDao.insert(
                GithubRepoEntity(
                    id = 0,
                    name = "name",
                    stars = "stars",
                    description = "description",
                )
            )
            uiState.value = UiState.Content(
                buttonLabel = repository.getSomeTextFromRepo(),
                text = "xxx: ${githubRepoDao.getAll()}",
            )
        }
    }

    internal sealed class UiState {
        data object Loading : UiState()
        data class Content(
            val buttonLabel: String,
            val text: String,
        ) : UiState()

        data object Error : UiState()
    }
}
