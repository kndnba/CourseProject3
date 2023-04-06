package com.bignerdranch.android.courseproject.data.remote

import javax.inject.Inject

class EpisodesRemoteDataSource @Inject constructor(
    private val episodesService: ApiService
): BaseDataSource() {

    suspend fun getEpisodes(page: Int) = getResult { episodesService.getAllEpisodes(page) }
    suspend fun getEpisode(id: Int) = getResult { episodesService.getEpisode(id) }
}