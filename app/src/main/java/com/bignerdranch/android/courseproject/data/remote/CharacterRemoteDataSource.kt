package com.bignerdranch.android.courseproject.data.remote

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseDataSource() {

    suspend fun getCharacters() = getResult { apiService.getAllCharacters() }
    suspend fun getCharacter(id: Int) = getResult { apiService.getCharacter(id) }
}