package com.szoldapps.weliwriterkmp

import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoDao
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoEntity

class MainRepositoryImpl(
    private val githubRepoDao: GithubRepoDao,
) {

    suspend fun getGithubRepoEntities(): List<GithubRepoEntity> {
        return githubRepoDao.getAll()
    }

    suspend fun insertGithubRepoEntity(
        entity: GithubRepoEntity = GithubRepoEntity(
            id = 0,
            name = "name",
            stars = "stars",
            description = "description",
        )
    ) {
        githubRepoDao.insert(item = entity)
    }
}
