package com.bignerdranch.android.courseproject.data.remote

import javax.inject.Inject

class EpisodesRemoteDataSource @Inject constructor(
    private val episodesService: ApiService
): BaseDataSource() {

    suspend fun getEpisodes() = getResult { episodesService.getAllEpisodes() }
    suspend fun getEpisode(id: Int) = getResult { episodesService.getEpisode(id) }
}