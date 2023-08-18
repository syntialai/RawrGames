package com.syntia.rawrgames.di

import android.content.Context
import com.syntia.rawrgames.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun appDatabase(@ApplicationContext context: Context): AppDatabase {
    return AppDatabase.buildDatabase(context)
  }

  @Provides
  fun gameDao(appDatabase: AppDatabase) = appDatabase.gameDao()
}