package com.szoldapps.weliwriterkmp.di

import com.szoldapps.weliwriterkmp.MainViewModel
import com.szoldapps.weliwriterkmp.MainRepositoryImpl
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModules(appDatabase: AppDatabase) = module {
    singleOf(::MainRepositoryImpl)
    viewModelOf(::MainViewModel)

    // DAOs
    single { appDatabase.getGithubRepoDao() }
    single { appDatabase.playerDao() }
    single { appDatabase.playerGameDao() }
    single { appDatabase.matchDao() }
    single { appDatabase.gameDao() }
    single { appDatabase.roundDao() }
    single { appDatabase.roundValueDao() }
}
