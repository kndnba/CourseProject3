package com.bignerdranch.android.courseproject.data.remote

import javax.inject.Inject

class LocationsRemoteDataSource @Inject constructor(
    private val locationsService: ApiService
): BaseDataSource() {

    suspend fun getLocations(page: Int) = getResult { locationsService.getAllLocations(page) }
    suspend fun getLocation(id: Int) = getResult { locationsService.getLocation(id) }
}