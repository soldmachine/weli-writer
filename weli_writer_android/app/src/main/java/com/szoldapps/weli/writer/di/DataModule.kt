package com.szoldapps.weli.writer.di

import android.content.Context
import com.szoldapps.weli.writer.data.db.AppDatabase
import com.szoldapps.weli.writer.data.db.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.createDatabase(appContext)

    @Provides
    fun providePlayerDao(appDatabase: AppDatabase): PlayerDao = appDatabase.playerDao()

    @Provides
    fun providePlayerGameDao(appDatabase: AppDatabase): PlayerGameDao = appDatabase.playerGameDao()

    @Provides
    fun provideMatchDao(appDatabase: AppDatabase): MatchDao = appDatabase.matchDao()

    @Provides
    fun provideGameDao(appDatabase: AppDatabase): GameDao = appDatabase.gameDao()

    @Provides
    fun provideRoundDao(appDatabase: AppDatabase): RoundDao = appDatabase.roundDao()

    @Provides
    fun provideRoundValueDao(appDatabase: AppDatabase): RoundValueDao = appDatabase.roundValueDao()

}
