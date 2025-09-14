package com.szoldapps.weliwriterkmp

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val repository: SomeRepoImpl,
) : ViewModel() {

    fun getTextFromRepo() = repository.getSomeTextFromRepo()
}
