package com.bignerdranch.android.courseproject.data.interactor

import com.bignerdranch.android.courseproject.data.entities.Character
import com.bignerdranch.android.courseproject.data.local.CharacterDao
import com.bignerdranch.android.courseproject.data.remote.CharacterRemoteDataSource
import com.bignerdranch.android.courseproject.utils.performGetOperation
import javax.inject.Inject

class CharacterInteractor @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCharacters(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllCharacters() },
        networkCall = { remoteDataSource.getCharacters(page) },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}