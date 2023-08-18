package com.syntia.rawrgames.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.syntia.rawrgames.data.network.ApiConstant
import com.syntia.rawrgames.data.network.ApiInterceptor
import com.syntia.rawrgames.data.network.GameService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun gson(): Gson {
    return GsonBuilder().create()
  }

  @Provides
  @Singleton
  fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun apiInterceptor() = ApiInterceptor()

  @Provides
  fun okHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: ApiInterceptor,
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .addInterceptor(apiInterceptor)
    .build()

  @Provides
  fun retrofitBuilder(
    gson: Gson,
    okHttpClient: OkHttpClient
  ): Retrofit = Retrofit.Builder()
    .baseUrl(ApiConstant.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

  @Provides
  fun gameService(retrofit: Retrofit): GameService = retrofit.create(GameService::class.java)
}