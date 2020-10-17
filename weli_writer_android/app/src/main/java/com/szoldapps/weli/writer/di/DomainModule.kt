package com.szoldapps.weli.writer.di

import com.szoldapps.weli.writer.data.WeliRepositoryImpl
import com.szoldapps.weli.writer.domain.WeliRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun provideWeliRepository(weliRepositoryImpl: WeliRepositoryImpl): WeliRepository
}
