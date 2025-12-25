package com.szoldapps.weliwriterkmp.di

import com.szoldapps.weliwriterkmp.MainViewModel
import com.szoldapps.weliwriterkmp.MainRepositoryImpl
import com.szoldapps.weliwriterkmp.appDatabase.AppDatabase
import com.szoldapps.weliwriterkmp.appDatabase.GithubRepoDao
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModules(appDatabase: AppDatabase) = module {
    single<GithubRepoDao> { appDatabase.getDao() }
    singleOf(::MainRepositoryImpl)
    viewModelOf(::MainViewModel)
}
