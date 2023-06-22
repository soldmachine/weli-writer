package com.szoldapps.weli.writer.di

import com.szoldapps.weli.writer.data.MatchRepositoryImpl
import com.szoldapps.weli.writer.data.WeliRepositoryImpl
import com.szoldapps.weli.writer.domain.MatchRepository
import com.szoldapps.weli.writer.domain.WeliRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun provideWeliRepository(weliRepositoryImpl: WeliRepositoryImpl): WeliRepository

    @Binds
    abstract fun provideMatchRepository(matchRepositoryImpl: MatchRepositoryImpl): MatchRepository
}
