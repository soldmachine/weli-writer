package com.szoldapps.weliwriterkmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoDao
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoEntity
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: SomeRepoImpl,
    private val githubRepoDao: GithubRepoDao,
) : ViewModel() {

    fun getTextFromRepo(): String {
        viewModelScope.launch {
            githubRepoDao.insert(
                GithubRepoEntity(
                    id = 0,
                    name = "name",
                    stars = "stars",
                    description = "description",
                )
            )
            println("xxx: ${githubRepoDao.getAll()}")
        }
        return repository.getSomeTextFromRepo()
    }
}
