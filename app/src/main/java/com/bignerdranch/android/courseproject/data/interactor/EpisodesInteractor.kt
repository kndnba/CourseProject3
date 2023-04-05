package com.bignerdranch.android.courseproject.data.interactor

import com.bignerdranch.android.courseproject.data.local.CharacterDao
import com.bignerdranch.android.courseproject.data.local.EpisodesDao
import com.bignerdranch.android.courseproject.data.remote.EpisodesRemoteDataSource
import com.bignerdranch.android.courseproject.utils.performGetOperation
import javax.inject.Inject

class EpisodesInteractor @Inject constructor(
    private val remoteDataSource: EpisodesRemoteDataSource,
    private val localDataSource: EpisodesDao
) {

    fun getEpisode(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getEpisode(id) },
        networkCall = { remoteDataSource.getEpisode(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getEpisodes(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllEpisodes() },
        networkCall = { remoteDataSource.getEpisodes(page) },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}