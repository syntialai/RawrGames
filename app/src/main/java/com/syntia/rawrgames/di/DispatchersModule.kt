package com.syntia.rawrgames.di

import com.syntia.rawrgames.data.network.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

  @Provides
  @Singleton
  fun coroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers()
}