package com.bignerdranch.android.courseproject.di

import android.content.Context
import com.bignerdranch.android.courseproject.data.local.*
import com.bignerdranch.android.courseproject.data.remote.CharacterRemoteDataSource
import com.bignerdranch.android.courseproject.data.remote.ApiService
import com.bignerdranch.android.courseproject.data.remote.EpisodesRemoteDataSource
import com.bignerdranch.android.courseproject.data.remote.LocationsRemoteDataSource
import com.bignerdranch.android.courseproject.data.repository.CharacterRepository
import com.bignerdranch.android.courseproject.data.repository.EpisodesRepository
import com.bignerdranch.android.courseproject.data.repository.LocationsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : ApiService = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(apiService: ApiService) = CharacterRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideCharacterDao(@ApplicationContext appContext: Context): CharacterDao = AppDatabase.getDatabase(appContext).characterDao()

    @Singleton
    @Provides
    fun provideLocationsDao(@ApplicationContext appContext: Context): LocationsDao = LocationsDatabase.getDatabase(appContext).getLocationsDao()

    @Singleton
    @Provides
    fun provideEpisodesDao(@ApplicationContext appContext: Context): EpisodesDao = EpisodesDatabase.getDatabase(appContext).getEpisodesDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: CharacterRemoteDataSource,
                          localDataSource: CharacterDao): CharacterRepository =
        CharacterRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideLocationsRepository(remoteDataSource: LocationsRemoteDataSource,
                                   localDataSource: LocationsDao): LocationsRepository =
        LocationsRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideEpisodesRepository(remoteDataSource: EpisodesRemoteDataSource,
                                  localDataSource: EpisodesDao
    ): EpisodesRepository =
        EpisodesRepository(remoteDataSource, localDataSource)
}