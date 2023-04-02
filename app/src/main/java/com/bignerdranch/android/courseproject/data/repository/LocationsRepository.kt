package com.bignerdranch.android.courseproject.data.repository

import com.bignerdranch.android.courseproject.data.local.LocationsDao
import com.bignerdranch.android.courseproject.data.remote.LocationsRemoteDataSource
import com.bignerdranch.android.courseproject.utils.performGetOperation
import javax.inject.Inject

class LocationsRepository @Inject constructor(
    private val remoteDataSource: LocationsRemoteDataSource,
    private val localDataSource: LocationsDao
) {

    fun getLocation(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getLocation(id) },
        networkCall = { remoteDataSource.getLocation(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getLocations() = performGetOperation(
        databaseQuery = { localDataSource.getAllLocations() },
        networkCall = { remoteDataSource.getLocations() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}