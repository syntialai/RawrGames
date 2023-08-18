package com.syntia.rawrgames.di

import com.syntia.rawrgames.data.repo.GameRepository
import com.syntia.rawrgames.data.repo.GameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun gameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository
}