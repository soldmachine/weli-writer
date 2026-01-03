package com.szoldapps.weliwriterkmp.di

import com.szoldapps.weliwriterkmp.data.db.setup.AppDatabase
import com.szoldapps.weliwriterkmp.data.MatchRepositoryImpl
import com.szoldapps.weliwriterkmp.data.WeliRepositoryImpl
import com.szoldapps.weliwriterkmp.domain.MatchRepository
import com.szoldapps.weliwriterkmp.domain.WeliRepository
import com.szoldapps.weliwriterkmp.presentation.match_list.MatchListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun appModules(appDatabase: AppDatabase) = module {

    // ViewModels
    viewModelOf(::MatchListViewModel)

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
    single { appDatabase.playerDao() }
    single { appDatabase.playerGameDao() }
    single { appDatabase.matchDao() }
    single { appDatabase.gameDao() }
    single { appDatabase.roundDao() }
    single { appDatabase.roundValueDao() }
}
