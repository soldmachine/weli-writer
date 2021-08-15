package com.szoldapps.weli.writer.di

import android.content.Context
import com.szoldapps.weli.writer.data.db.AppDatabase
import com.szoldapps.weli.writer.data.db.dao.GameDao
import com.szoldapps.weli.writer.data.db.dao.MatchDao
import com.szoldapps.weli.writer.data.db.dao.PlayerDao
import com.szoldapps.weli.writer.data.db.dao.PlayerGameDao
import com.szoldapps.weli.writer.data.db.dao.RoundDao
import com.szoldapps.weli.writer.data.db.dao.RoundValueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
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
