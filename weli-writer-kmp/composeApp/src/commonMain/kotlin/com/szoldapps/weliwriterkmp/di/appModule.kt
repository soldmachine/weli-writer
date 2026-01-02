package com.szoldapps.weliwriterkmp.di

import com.szoldapps.weliwriterkmp.MainRepositoryImpl
import com.szoldapps.weliwriterkmp.MainViewModel
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import com.szoldapps.weliwriterkmp.data.MatchRepositoryImpl
import com.szoldapps.weliwriterkmp.data.WeliRepositoryImpl
import com.szoldapps.weliwriterkmp.domain.MatchRepository
import com.szoldapps.weliwriterkmp.domain.WeliRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun appModules(appDatabase: AppDatabase) = module {
    singleOf(::MainRepositoryImpl)
    viewModelOf(::MainViewModel)

    // Repositories
    single<WeliRepository> {
        WeliRepositoryImpl(
            playerDao = get(),
            playerGameDao = get(),
            gameDao = get(),
            roundDao = get(),
            roundValueDao = get(),
        )
    }
    single<MatchRepository> {
        MatchRepositoryImpl(
            matchDao = get(),
        )
    }

    // DAOs
    single { appDatabase.getGithubRepoDao() }
    single { appDatabase.playerDao() }
    single { appDatabase.playerGameDao() }
    single { appDatabase.matchDao() }
    single { appDatabase.gameDao() }
    single { appDatabase.roundDao() }
    single { appDatabase.roundValueDao() }
}
