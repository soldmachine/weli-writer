package com.szoldapps.weliwriterkmp.di

import com.szoldapps.weliwriterkmp.MainViewModel
import com.szoldapps.weliwriterkmp.SomeRepoImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::SomeRepoImpl)
    viewModelOf(::MainViewModel)
}
